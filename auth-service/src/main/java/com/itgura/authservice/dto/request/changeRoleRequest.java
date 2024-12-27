package com.itgura.authservice.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class changeRoleRequest {
    @JsonProperty("username(email)")
    private String username;
    @JsonProperty("changeRole")
    private String changeRole;
}
