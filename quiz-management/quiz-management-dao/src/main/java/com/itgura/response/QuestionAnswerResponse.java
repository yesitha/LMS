package com.itgura.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerResponse {
    private UUID id;
    private UUID studentId;
    private String answerText;
    private UUID fileUrl; // URL to the file or image with the answer
    private Timestamp submittedAt;
}

