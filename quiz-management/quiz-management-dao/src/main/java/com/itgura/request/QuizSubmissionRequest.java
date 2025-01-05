package com.itgura.request;
import lombok.*;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QuizSubmissionRequest {
    private UUID quizId;
    private UUID studentId;
    private List<QuestionAnswerDTO> answers;
}