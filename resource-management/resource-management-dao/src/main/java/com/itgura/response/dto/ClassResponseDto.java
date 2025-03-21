package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.enums.ContentAccessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponseDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("class_name")
    private String className;
    @JsonProperty("content_access_type")
    private ContentAccessType contentAccesstype;
    @JsonProperty("image")
    private String image;

}
