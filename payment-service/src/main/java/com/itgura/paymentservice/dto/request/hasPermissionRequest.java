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
public class hasPermissionRequest {

//    @JsonProperty(value = "student_id")
//    @NotNull(message = "student id is required")
//    @ApiModelProperty(required = true, value = "student id is required")
//    private UUID studentId;

   @JsonProperty(value = "content_id")
    @NotNull(message = "content id is required")
    @ApiModelProperty(required = true, value = "content id is required")
    private UUID contentId;

}
