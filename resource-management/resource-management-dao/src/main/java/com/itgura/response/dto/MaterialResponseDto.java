package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.enums.ContentAccessType;
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
public class MaterialResponseDto {
    @JsonProperty("material_id")
    private UUID id;
    @JsonProperty("material_name")
    private String materialName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("material_url")
    private String reference;
    @JsonProperty("file")
    private Object file;
    @JsonProperty("material_type")
    private MaterialTypeResponseDto materialType;
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
    @JsonProperty("content_access_type")
    private ContentAccessType contentAccesstype;
}
