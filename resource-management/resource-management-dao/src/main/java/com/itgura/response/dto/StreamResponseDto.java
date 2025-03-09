package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamResponseDto {
    @JsonProperty("stream_id")
    private UUID streamId;
    @JsonProperty("stream")
    private String stream;
}