package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.enums.ContentAccessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRequest {
    @JsonProperty("start_date_and_time")
    private Date startDateAndTime;
    @JsonProperty("session_name")
    private String sessionName;
    @JsonProperty("session_number")
    private Integer sessionNumber;
    @JsonProperty("description")
    private String description;
    @JsonProperty("topic")
    private String topic;
    @JsonProperty("is_available_for_student")
    private Boolean isAvailableForStudent;
    @JsonProperty("content_access_type")
    private ContentAccessType contentAccesstype;
}
