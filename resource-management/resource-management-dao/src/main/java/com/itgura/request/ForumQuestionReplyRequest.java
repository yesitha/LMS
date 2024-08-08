package com.itgura.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumQuestionReplyRequest {
    @JsonProperty("is_new")
    private boolean isNew;
    @JsonProperty("reply")
    private String reply;
    @JsonProperty("forum_reply_image_list")
    private List<ForumQuestionImageRequest> forumReplyImageRequest;
}
