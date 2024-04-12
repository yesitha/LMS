package com.itgura.dms_mediator.alfresco.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    @JsonProperty("errorKey")
    private String errorKey;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("briefSummary")
    private String briefSummary;

    @JsonProperty("stackTrace")
    private String stackTrace;

    @JsonProperty("descriptionURL")
    private String descriptionURL;
}

