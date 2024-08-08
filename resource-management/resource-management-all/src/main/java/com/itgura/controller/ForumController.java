package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.ForumQuestionReplyRequest;
import com.itgura.request.ForumQuestionRequest;
import com.itgura.response.dto.ForumQuestionReplyResponseDto;
import com.itgura.response.dto.ForumQuestionResponseDto;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itgura.service.ForumService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class ForumController {
    @Autowired
    private ForumService forumService;
    @GetMapping(ResourceManagementURI.FORUM + URIPrefix.GET_ALL)
    public AppResponse<List<ForumQuestionResponseDto>> getAll() {
        try {
            List<ForumQuestionResponseDto> response = forumService.getAll();
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.FORUM +ResourceManagementURI.MY +  URIPrefix.GET_ALL)
    public AppResponse<List<ForumQuestionResponseDto>> getMyQuestions() {
        try {
            List<ForumQuestionResponseDto> response = forumService.getMyQuestions();
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PostMapping(ResourceManagementURI.FORUM + URIPrefix.CREATE)
    public AppResponse<String> createForum(@RequestBody AppRequest<ForumQuestionRequest> request) {
        try {
            String s = forumService.addQuestion(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.FORUM + URIPrefix.UPDATE+URIPrefix.ID)
    public AppResponse<String> updateForum(@PathVariable UUID id, @RequestBody AppRequest<ForumQuestionRequest> request) {
        try {
            String s = forumService.updateQuestion(id, request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.FORUM + URIPrefix.DELETE+URIPrefix.ID)
    public AppResponse<String> delete(@PathVariable UUID id) {
        try {
            String s = forumService.deleteQuestion(id);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.FORUM +ResourceManagementURI.REPLY+URIPrefix.CREATE+URIPrefix.IS_REPLY_FOR_QUESTION+URIPrefix.REPLY_ID)
    public AppResponse<String> createReply(@PathVariable UUID replyId, @PathVariable Boolean isReplyForQuestion, @RequestBody AppRequest<ForumQuestionReplyRequest> request) {
        try {
            String s = forumService.createReply(replyId, isReplyForQuestion, request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.FORUM +ResourceManagementURI.REPLY+URIPrefix.DELETE+URIPrefix.REPLY_ID)
    public AppResponse<String> deleteReply(@PathVariable UUID replyId) {
        try {
            String s = forumService.deleteReply(replyId);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.FORUM +ResourceManagementURI.REPLY+URIPrefix.UPDATE+URIPrefix.REPLY_ID)
    public AppResponse<String> updateReply(@PathVariable UUID replyId, @RequestBody AppRequest<ForumQuestionReplyRequest> request) {
        try {
            String s = forumService.updateReply(replyId, request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(ResourceManagementURI.FORUM +ResourceManagementURI.REPLY+URIPrefix.GET_ALL+URIPrefix.QUESTION_ID)
    public AppResponse<List<ForumQuestionReplyResponseDto>> getReplies(@PathVariable UUID questionId) {
        try {
            List<ForumQuestionReplyResponseDto> response = forumService.getReplies(questionId);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
