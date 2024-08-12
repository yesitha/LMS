package com.itgura.request;

import com.itgura.entity.QuestionType;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateQuizRequest {

    private String title;
    private String description;
    private Double totalMarks;
    private UUID createdBy;
    private List<QuizQuestionDTO> questions;
    private List<UUID> classIds;
    private Long duration;
    private Timestamp deadline;

    @Data
    public static class QuizQuestionDTO {
        private String questionText;
        private QuestionType questionType; // MCQ, ESSAY, etc.
        private Double marks;
        private List<MCQOptionDTO> options; // Only applicable for MCQ questions
        private List<QuestionFileDTO> files; // Applicable for both MCQ and Essay questions
    }

    @Data
    public static class MCQOptionDTO {
        private String optionText;
        private boolean isCorrect;
        private QuestionFileDTO file; // Optional, for images or other files
    }

    @Data
    public static class QuestionFileDTO {
        private UUID fileUrl;
        private String fileType; // e.g., image/jpeg, application/pdf
    }
}

