package com.itgura.request;
import lombok.*;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QuestionAnswerDTO {
    private UUID questionId;
    private String answerText;
    private UUID fileUrl; // optional for questions with file-based answers
    private Boolean isCorrect; // for MCQ answers
}