package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizeQuestionRequest {
    @JsonProperty("is_multiple_choice")
    private boolean isMultipleChoice;
    @JsonProperty("is_question_image")
    private boolean isQuestionImage;
    @JsonProperty("text_question")
    private String textQuestion;
    @JsonProperty("image_question")
    private QuizeImageRequest imageQuestion;
    @JsonProperty("total_marks")
    private double totalMarks;
    @JsonProperty("images_of_question")
    private List<QuizeImageRequest> imagesOfQuestion;
    @JsonProperty("answers")
    private List<QuestionAnswerRequest> answers;
}
