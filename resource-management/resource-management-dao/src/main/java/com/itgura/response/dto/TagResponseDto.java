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
public class TagResponseDto {
    @JsonProperty("tag_id")
    private UUID tagId;

    @JsonProperty("tag_name")
    private String tagName;

}
