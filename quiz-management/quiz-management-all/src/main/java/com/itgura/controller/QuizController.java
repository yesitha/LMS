package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.request.CreateQuizRequest;
import com.itgura.response.QuizResponse;
import com.itgura.service.QuizService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.QUIZ_SERVICE)
public class QuizController {
    @Autowired
    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @PostMapping(ResourceManagementURI.QUIZ + ResourceManagementURI.CREATE)
    public AppResponse<String> createQuiz(@RequestBody AppRequest<CreateQuizRequest> request) {
        try {
            String response = this.quizService.createQuiz(request.getData());
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @GetMapping(ResourceManagementURI.QUIZ + ResourceManagementURI.GET_BY_ID)
    public AppResponse<QuizResponse> getQuizById(@RequestParam("id") UUID id) {
        try {
            QuizResponse quiz = quizService.getQuizById(id);
            if (quiz != null) {
                return AppResponse.ok(quiz);
            } else {
                return AppResponse.error(null, "Quiz not found", "Not Found", "404", "");
            }
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
