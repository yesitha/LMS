package com.itgura.service;

import com.itgura.request.CreateAssignmentRequest;
import com.itgura.request.CreateQuizRequest;
import com.itgura.response.QuizResponse;

import java.util.UUID;

public interface QuizService {
    public String createQuiz(CreateQuizRequest request);
    QuizResponse getQuizById(UUID id);
}
