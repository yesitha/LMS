package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialRequest {
    @JsonProperty("material_name")
    private String materialName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_available_for_student")
    private Boolean isAvailableForStudent;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("material_type_id")
    @Lob
    private UUID materialType;
}
