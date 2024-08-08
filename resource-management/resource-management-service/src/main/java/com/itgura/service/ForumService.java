package com.itgura.service;

import com.itgura.dto.AppRequest;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.ForumQuestionReplyRequest;
import com.itgura.request.ForumQuestionRequest;
import com.itgura.response.dto.ForumQuestionReplyResponseDto;
import com.itgura.response.dto.ForumQuestionResponseDto;

import java.util.List;
import java.util.UUID;

public interface ForumService {
    String addQuestion(ForumQuestionRequest forumQuestionRequest);
    String updateQuestion(UUID ForumQuestionId, ForumQuestionRequest forumQuestionRequest);
    public String deleteQuestion(UUID questionId) throws ValueNotExistException;

    List<ForumQuestionResponseDto> getAll();

    List<ForumQuestionResponseDto> getMyQuestions();

    String createReply(UUID contentId, Boolean isQuestion, ForumQuestionReplyRequest request);
    String deleteReply(UUID contentId);
    String updateReply(UUID contentId,   ForumQuestionReplyRequest request);

    List<ForumQuestionReplyResponseDto> getReplies(UUID questionId);
}
