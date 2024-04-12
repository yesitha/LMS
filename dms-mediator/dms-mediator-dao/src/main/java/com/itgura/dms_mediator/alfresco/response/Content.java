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
public class Content {

    @JsonProperty("mimeType")
    private String mimeType;

    @JsonProperty("mimeTypeName")
    private String mimeTypeName;

    @JsonProperty("sizeInBytes")
    private Long sizeInBytes;

    @JsonProperty("encoding")
    private String encoding;

}
