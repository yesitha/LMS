package com.itgura.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private UUID id;
    private String questionText;
    private String questionType; // MCQ, ESSAY, etc.
    private Double marks;
    private UUID createdBy;
    private Timestamp createdAt;
    private UUID updatedBy;
    private Timestamp updatedAt;
    private List<MCQOptionResponse> options; // List of options for MCQ questions
    private List<QuestionAnswerResponse> answers; // List of answers
    private List<QuestionFileResponse> images; // List of images associated with the question
}

