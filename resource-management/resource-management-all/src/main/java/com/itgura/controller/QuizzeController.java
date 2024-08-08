package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.LessonRequest;
import com.itgura.request.QuizeRequest;
import com.itgura.response.dto.MaterialTypeResponseDto;
import com.itgura.service.QuizeService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class QuizzeController {
    @Autowired
    private QuizeService quizeService;

    @PostMapping(ResourceManagementURI.QUIZZES + URIPrefix.CREATE)
    public AppResponse<String> createQuize(@RequestBody AppRequest<QuizeRequest> request) {
        try {
            String s = quizeService.create();
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
