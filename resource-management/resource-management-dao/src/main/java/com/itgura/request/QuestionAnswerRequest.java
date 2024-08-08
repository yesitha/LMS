package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerRequest {

    @JsonProperty("is_answer_image")
    private boolean isAnswerImage;
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("image_answer")
    private QuizeImageRequest imageAnswer;
    @JsonProperty("is_correct_answer")
    private boolean isCorrectAnswer;
}
