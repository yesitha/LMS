package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.ForumQuestionRequest;
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
    @PostMapping(ResourceManagementURI.FORUM + URIPrefix.UPDATE)
    public AppResponse<String> updateForum(@PathVariable UUID questionId, @RequestBody AppRequest<ForumQuestionRequest> request) {
        try {
            String s = forumService.updateQuestion(questionId, request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.FORUM + URIPrefix.DELETE)
    public AppResponse<String> delete(@PathVariable UUID questionId) {
        try {
            String s = forumService.deleteQuestion(questionId);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}