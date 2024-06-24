package com.itgura.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class hasPermissionResponse {

    @JsonProperty("contentId")
    private UUID contentId;

    @JsonProperty("hasPermission")
    private Boolean hasPermission;

}
