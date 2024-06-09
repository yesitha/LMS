package com.itgura.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class getPaidMothRequest {
    @JsonProperty(value = "studentEmail")
    @NotNull(message = "student email is required")
    @ApiModelProperty(required = true, value = "student email is required")
    private String studentEmail;

    @JsonProperty(value = "classId")
    @NotNull(message = "class id is required")
    @ApiModelProperty(required = true, value = "class id is required")
    private UUID classId;
}