package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoUploadRequest {

    @NotNull(message = "Original file name is required")
    @JsonProperty("original_file_name")
    private String OriginalFileName;

    @NotNull(message = "Signed URL expire time required")
    @JsonProperty("signed_url_expire_time")
    private Integer signedUrlExpireTime;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("session_id")
    private UUID sessionId;

    @JsonProperty("is_available_for_students")
    private Boolean isAvailableForStudents;
}
