package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ForumImageResponseDto {
    @JsonProperty("forum_image_id")
    private UUID forumImageId;
    @JsonProperty("image")
    private byte[] image;
}
