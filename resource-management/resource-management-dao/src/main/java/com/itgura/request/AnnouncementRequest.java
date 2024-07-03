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
public class AnnouncementRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;


}
