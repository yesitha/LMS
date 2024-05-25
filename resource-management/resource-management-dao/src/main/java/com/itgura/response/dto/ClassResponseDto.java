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
public class ClassResponseDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("class_name")
    private String className;

}
