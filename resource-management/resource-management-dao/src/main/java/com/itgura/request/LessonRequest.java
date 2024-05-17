package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest {
    @JsonProperty("lesson_name")
    private String lessonName;
    @JsonProperty("lesson_number")
    private Integer lessonNumber;
    @JsonProperty("description")
    private String description;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("lesson_duration")
    private Integer lessonDuration;
    @JsonProperty("class_id")
    @Lob
    private UUID classId;
    @JsonProperty("session_list")
    private Double price;
    @JsonProperty("is_available_for_users")
    private Boolean isAvailableForUsers;

}
