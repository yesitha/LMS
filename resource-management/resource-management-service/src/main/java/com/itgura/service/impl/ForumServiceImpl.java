package com.itgura.service.impl;

import com.itgura.entity.ForumQuestion;
import com.itgura.entity.ForumImage;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ForumImageRepository;
import com.itgura.repository.ForumQuestionRepository;
import com.itgura.request.ForumQuestionImageRequest;
import com.itgura.request.ForumQuestionRequest;

import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.ForumQuestionResponseDto;
import com.itgura.service.ForumService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private ForumQuestionRepository forumQuestionRepository;
    @Autowired
    private ForumImageRepository forumImageRepository;

    @Override
    @Transactional
    public String addQuestion(ForumQuestionRequest forumQuestionRequest) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                ForumQuestion forumQuestion = new ForumQuestion();
                forumQuestion.setQuestion(forumQuestionRequest.getQuestion());
                forumQuestion.setCreatedBy(loggedUserDetails.getUserId());
                forumQuestion.setCreatedOn(new Date(System.currentTimeMillis()));
                ForumQuestion saveForum = forumQuestionRepository.save(forumQuestion);

                if (!forumQuestionRequest.getForumQuestionImageRequest().isEmpty()) {
                    for (ForumQuestionImageRequest req : forumQuestionRequest.getForumQuestionImageRequest()) {
                        ForumImage forumImage = new ForumImage();
                        forumImage.setForumQuestion(saveForum);
                        forumImage.setImage(req.getImage());
                        forumImageRepository.save(forumImage);
                    }
                }
                return "Question added successfully";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateQuestion(UUID questionId, ForumQuestionRequest forumQuestionRequest) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                Optional<ForumQuestion> fq = forumQuestionRepository.findById(questionId);
                if (fq.isPresent()) {
                    ForumQuestion forumQuestion = fq.get();
                    if (forumQuestion.getCreatedBy().equals(loggedUserDetails.getUserId())) {
                        forumQuestion.setQuestion(forumQuestionRequest.getQuestion());
                        forumQuestion.setLastModifiedBy(loggedUserDetails.getUserId());
                        forumQuestion.setLastModifiedOn(new Date(System.currentTimeMillis()));
                        ForumQuestion save = forumQuestionRepository.save(forumQuestion);
                        for (ForumQuestionImageRequest img : forumQuestionRequest.getForumQuestionImageRequest()) {
                            if (img.isNew() && !img.isDeleted()) {
                                ForumImage forumImage = new ForumImage();
                                forumImage.setForumQuestion(save);
                                forumImage.setImage(img.getImage());
                                forumImageRepository.save(forumImage);
                            } else if (!img.isNew()) {
                                Optional<ForumImage> byId = forumImageRepository.findById(img.getId());
                                if (byId.isPresent()) {
                                    ForumImage forumImage = byId.get();
                                    forumImage.setImage(img.getImage());
                                } else {
                                    throw new ValueNotExistException("Image not found");
                                }
                            } else if (img.isDeleted()) {
                                forumImageRepository.deleteById(img.getId());
                            }

                        }
                        return "update question";
                    } else {
                        throw new IllegalAccessException("You are not authorized to edit this question.");
                    }
                } else {
                    throw new ValueNotExistException("Question not found");
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public String deleteQuestion(UUID questionId) throws ValueNotExistException {
        Optional<ForumQuestion> forumQuestion = forumQuestionRepository.findById(questionId);
        if (forumQuestion.isPresent()) {
            forumQuestionRepository.delete(forumQuestion.get());
            return "Question deleted successfully";
        } else {
            throw new ValueNotExistException("Question not found");
        }
    }

    @Override
    public List<ForumQuestionResponseDto> getAll() {
        return null;
    }

    @Override
    public List<ForumQuestionResponseDto> getMyQuestions() {
        return null;
    }
}