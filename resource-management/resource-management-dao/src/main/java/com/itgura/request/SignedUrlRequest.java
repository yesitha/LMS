package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignedUrlRequest {
    @JsonProperty("filePath")
    String filePath;
    @JsonProperty("userIpAddress")
    String userIpAddress;
    @JsonProperty("expiresInHours")
    int expiresInHours;
}
