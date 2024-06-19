package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class hasPermissionRequest {

//    @JsonProperty(value = "user_email")
//    @NotNull(message = "user_email is required")
//    @ApiModelProperty(required = true, value = "user_email is required")
//    private UUID studentId;

   @JsonProperty(value = "content_ids")
    @NotNull(message = "content ids is required")
    @ApiModelProperty(required = true, value = "content ids is required")
    private List<UUID> contentIds;

}
