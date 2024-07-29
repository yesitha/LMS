package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.enums.ContentAccessType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassRequest {
    @JsonProperty("class_name")
    private String className;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("fees")
    private Double fees;
    @JsonProperty("content_access_type")
    private ContentAccessType contentAccesstype;


}
