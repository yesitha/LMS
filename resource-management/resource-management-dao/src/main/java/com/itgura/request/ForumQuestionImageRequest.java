package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumQuestionImageRequest {
    @JsonProperty("existing_image_id")
    private UUID id;
    @JsonProperty( "image")
    private UUID image;
    @JsonProperty("is_new")
    private boolean isNew;
    @JsonProperty("is_deleted")
    private boolean isDeleted;
}
