package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizeRequest {
    @JsonProperty("quize_name")
    private String quizeName;
    private String description;
    @JsonProperty("is_available_for_student")
    private Boolean isAvailableForStudent;
    @JsonProperty("duration_minutes")
    private double durationMinutes;
    @JsonProperty("deadline")
    private Date deadline;
    @JsonProperty("lesson_id")
    private UUID lessonId;
    @JsonProperty("questions")
    private List<QuizeQuestionRequest> questions;

}
