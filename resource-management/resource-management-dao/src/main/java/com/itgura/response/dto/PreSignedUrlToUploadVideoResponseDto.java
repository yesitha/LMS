package com.itgura.response.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreSignedUrlToUploadVideoResponseDto {

    @JsonProperty("video_uuid")
    private UUID id;
    @JsonProperty("pre_signed_url")
    private String preSignedUrl;
}
