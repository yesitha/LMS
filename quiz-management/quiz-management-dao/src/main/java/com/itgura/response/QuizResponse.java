package com.itgura.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private UUID id;
    private String title;
    private String description;
    private Double totalMarks;
    private List<UUID> classIds; // UUIDs of associated classes
    private List<QuestionResponse> questions; // List of questions associated with the quiz
}

