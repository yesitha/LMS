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
public class MaterialTypeResponseDto {
    @JsonProperty("material_type_id")
    private UUID id;
    @JsonProperty("material_type")
    private String materialType;

}
