package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumQuestionReplyResponseDto {
    @JsonProperty("forum_question_reply_id")
    private UUID id;
    @JsonProperty("reply")
    private String reply;
    @JsonProperty("forum_image_list")
    private List<ForumImageResponseDto> forumImageList;
    @JsonProperty("reply_for_reply")
    private List<ForumQuestionReplyResponseDto> replyList;
    @JsonProperty("created_by_user_id")
    private UUID createdByUserId;
    @JsonProperty("created_by_user_name")
    private String createdByUserName;
    @JsonProperty("created_on")
    private Date createdOn;
    @JsonProperty("last_modified_by_user_id")
    private UUID lastModifiedByUserId;
    @JsonProperty("last_modified_by_user_name")
    private String lastModifiedByUserName;
    @JsonProperty("last_modified_on")
    private Date lastModifiedOn;
}
