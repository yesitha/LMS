package com.itgura.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ForumQuestionResponseDto {
    @JsonProperty("forum_question_id")
    private UUID id;
    @JsonProperty("question")
    private String question;

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

    @JsonProperty("forum_image_list")
    private List<ForumImageResponseDto> forumImageList;

    @JsonProperty("number_of_replies")
    private Integer numberOfReplies;

}
