package com.itgura.response.dto;

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
public class LessonResponseDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("lesson_name")
    private String lessonName;
    @JsonProperty("lesson_number")
    private Integer lessonNumber;
    @JsonProperty("lesson_description")
    private String lessonDescription;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    @JsonProperty("lesson_duration")
    private Integer lessonDuration;
    @JsonProperty("class_id")
    @Lob
    private UUID classId;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("is_available_for_logged_user")
    private Boolean isAvailableForLoggedUser;
    @JsonProperty("is_available_for_all_users")
    private Boolean isAvailableForAllUser;

    @JsonProperty("created_by_user_id")
    @Lob
    private UUID createdByUserId;
    @JsonProperty("created_on")
    private java.util.Date createdOn;
    @JsonProperty("last_modified_by_user_id")
    @Lob
    private UUID lastModifiedByUserId;
    @JsonProperty("last_modified_on")
    private java.util.Date lastModifiedOn;
    @JsonProperty("created_by_name")
    private String createdByName;
    @JsonProperty("updated_by_name")
    private String updatedByName;

}
