package com.itgura.response.dto;

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
public class SessionResponseDto {
    @JsonProperty("session_id")
    private UUID id;
    @JsonProperty("session_name")
    private String sessionName;
    @JsonProperty("topic")
    private String topic;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("date_and_time")
    private Date dateAndTime;
    @JsonProperty("session_number")
    private Integer sessionNumber;
    @JsonProperty("created_by_user_id")
    private UUID createdByUserId;
    @JsonProperty("created_by_user_name")
    private String createdByUserName;
    @JsonProperty("created_on")
    private Date createdOn;
    @JsonProperty("last_modified_by_user_id")
    private UUID lastModifiedByUserId;
    @JsonProperty("last_modified_by_user_name")
    private String lastModifiedByUserName;
    @JsonProperty("last_modified_on")
    private Date lastModifiedOn;
    @JsonProperty("is_available_for_students")
    private Boolean isAvailableForStudents;
    @JsonProperty("is_available_for_logged_user")
    private Boolean isAvailableForLoggedUser;
    @JsonProperty("material_list")
    private List<MaterialResponseDto> materialList;


}
