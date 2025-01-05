package com.itgura.service;

import com.itgura.exception.ValueNotFoundException;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.request.CreateQuizRequest;
import com.itgura.request.QuizSubmissionRequest;
import com.itgura.response.QuizResponse;
import com.itgura.response.QuizSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface QuizService {
    public String createQuiz(CreateQuizRequest request);
    QuizResponse getQuizById(UUID id);
    public void deleteQuiz(UUID id);
    public List<QuizSummaryDTO> getQuizzesByClassIds(List<UUID> classIds);
    public boolean updatePublishedStatus(UUID id, Boolean isPublished);
    public boolean answerToQuiz(QuizSubmissionRequest request) throws ValueNotFoundException;
}
