package com.itgura.service.impl;

import com.itgura.dto.AppRequest;
import com.itgura.entity.ForumQuestion;
import com.itgura.entity.ForumImage;
import com.itgura.entity.ForumQuestionReply;
import com.itgura.entity.Lesson;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ForumImageRepository;
import com.itgura.repository.ForumQuestionReplyRepository;
import com.itgura.repository.ForumQuestionRepository;
import com.itgura.request.ForumQuestionImageRequest;
import com.itgura.request.ForumQuestionReplyRequest;
import com.itgura.request.ForumQuestionRequest;

import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.ForumQuestionReplyResponseDto;
import com.itgura.response.dto.ForumQuestionResponseDto;
import com.itgura.response.dto.mapper.ForumQuestionMapper;
import com.itgura.response.dto.mapper.ForumQuestionReplyMapper;
import com.itgura.response.dto.mapper.LessonMapper;
import com.itgura.service.ForumService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
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
    @Autowired
    private ForumQuestionReplyRepository forumQuestionReplyRepository;

    @Override
    @Transactional
    public String addQuestion(ForumQuestionRequest forumQuestionRequest) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                if(forumQuestionRequest.isNew()) {
                    ForumQuestion forumQuestion = new ForumQuestion();
                    forumQuestion.setQuestion(forumQuestionRequest.getQuestion());
                    forumQuestion.setCreatedBy(loggedUserDetails.getUserId());
                    forumQuestion.setCreatedOn(new Date(System.currentTimeMillis()));
                    ForumQuestion saveForum = forumQuestionRepository.save(forumQuestion);

                    if (!forumQuestionRequest.getForumQuestionImageRequest().isEmpty()) {
                        for (ForumQuestionImageRequest req : forumQuestionRequest.getForumQuestionImageRequest()) {
                            if (req.isNew() && !req.isDeleted()){
                                ForumImage forumImage = new ForumImage();
                                forumImage.setForumQuestion(saveForum);
                                forumImage.setImage(req.getImage());
                                forumImageRepository.save(forumImage);
                            }
                        }
                    }
                    return "Question added successfully";
                } else{
                return "Save question only if new reply ";
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
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
                                System.out.println("img.isNew() && !img.isDeleted()");
                                ForumImage forumImage = new ForumImage();
                                forumImage.setForumQuestion(save);
                                forumImage.setImage(img.getImage());
                                forumImageRepository.save(forumImage);
                            } else if (!img.isNew() && !img.isDeleted()) {

                                Optional<ForumImage> byId = forumImageRepository.findById(img.getId());
                                if (byId.isPresent() ) {
                                    ForumImage forumImage = byId.get();
                                    forumImage.setImage(img.getImage());
                                } else {
                                    throw new ValueNotExistException("Image not found");
                                }
                            } else if (img.isDeleted() && !img.isNew()) {
                                System.out.println("img.isDeleted()");
                                Optional<ForumImage> existingImage = forumImageRepository.findById(img.getId());

                                if (existingImage.isPresent()) {
                                    forumImageRepository.delete(existingImage.get());
                                    System.out.println("deleted "+existingImage.get());
                                } else {
                                    throw new ValueNotExistException("Image not found");
                                }
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
    @Override
    @Transactional
    public String deleteQuestion(UUID questionId) throws ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }

            Optional<ForumQuestion> forumQuestion = forumQuestionRepository.findById(questionId);
            if (forumQuestion.isPresent()) {
                if ((loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER"))) {
                    forumQuestionRepository.delete(forumQuestion.get());
                    return "Question deleted successfully";
                } else if (forumQuestion.get().getCreatedBy().equals(loggedUserDetails.getUserId())) {
                    forumQuestionRepository.delete(forumQuestion.get());
                    return "Question deleted successfully";
                 } else {
                throw new IllegalAccessException("You are not authorized to edit this question.");
            }

            } else {
                throw new ValueNotExistException("Question not found");
            }
           }catch (Exception e){
                throw new RuntimeException(e);
            }

    }

    @Override
    @Transactional
    public List<ForumQuestionResponseDto> getAll() {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                   List<ForumQuestion> forumQuestions = forumQuestionRepository.findAll();
                    return ForumQuestionMapper.INSTANCE.toDtoList(forumQuestions);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    @Transactional
    public List<ForumQuestionResponseDto> getMyQuestions() {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                UUID userId = loggedUserDetails.getUserId();
                List<ForumQuestion> forumQuestions = forumQuestionRepository.findByCreatedBy(userId);
                return ForumQuestionMapper.INSTANCE.toDtoList(forumQuestions);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String createReply(UUID contentId, Boolean isQuestion, ForumQuestionReplyRequest request) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if(request.isNew()) {
                ForumQuestionReply forumQuestionReply = new ForumQuestionReply();
                forumQuestionReply.setReply(request.getReply());
                forumQuestionReply.setCreatedBy(loggedUserDetails.getUserId());
                forumQuestionReply.setCreatedOn(new Date(System.currentTimeMillis()));
                if (isQuestion) {
                    Optional<ForumQuestion> byId = forumQuestionRepository.findById(contentId);
                    if (byId.isPresent()) {
                        forumQuestionReply.setForumQuestion(byId.get());
                    } else {
                        throw new ValueNotExistException("Cannot find Forum question");
                    }
                } else {
                    Optional<ForumQuestionReply> byId = forumQuestionReplyRepository.findById(contentId);
                    if (byId.isPresent()) {
                        forumQuestionReply.setParentReply(byId.get());
                    } else {
                        throw new ValueNotExistException("Cannot find Forum question reply");
                    }

                }
                ForumQuestionReply saveReply = forumQuestionReplyRepository.save(forumQuestionReply);
                if (!request.getForumReplyImageRequest().isEmpty()) {
                    for (ForumQuestionImageRequest req : request.getForumReplyImageRequest()) {
                        if (req.isNew() && !req.isDeleted()){
                            ForumImage forumImage = new ForumImage();
                            forumImage.setForumQuestionReply(saveReply);
                            forumImage.setImage(req.getImage());
                            forumImageRepository.save(forumImage);
                        }
                    }
                }
                return "Reply added successfully";
            }else{
                return "save reply only if new reply ";
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String deleteReply(UUID contentId) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }

            Optional<ForumQuestionReply> byId = forumQuestionReplyRepository.findById(contentId);
            if (byId.isPresent()) {
                if ((loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER"))) {
                    forumQuestionReplyRepository.delete(byId.get());
                    return "Question reply deleted successfully";
                } else if (byId.get().getCreatedBy().equals(loggedUserDetails.getUserId())) {
                    forumQuestionReplyRepository.delete(byId.get());
                    return "Question reply deleted successfully";
                } else {
                    throw new IllegalAccessException("You are not authorized to edit this question reply.");
                }

            } else {
                throw new ValueNotExistException("Question reply not found");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String updateReply(UUID contentId, ForumQuestionReplyRequest request) {
        return null;
    }

    @Override
    @Transactional
    public List<ForumQuestionReplyResponseDto> getReplies(UUID questionId) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }else{
                Optional<ForumQuestion> byId = forumQuestionRepository.findById(questionId);
                if(byId.isPresent()) {
                    List<ForumQuestionReply> byForumQuestion = forumQuestionReplyRepository.findByForumQuestion(byId.get());
                    return ForumQuestionReplyMapper.INSTANCE.toDtoList(byForumQuestion);
                }else{
                    throw new ValueNotExistException("Question  not found");
                }
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}