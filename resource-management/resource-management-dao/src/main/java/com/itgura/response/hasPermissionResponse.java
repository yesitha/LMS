package com.itgura.response;

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

    private UUID contentId;
    private Boolean hasPermission;

}
