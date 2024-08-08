package com.itgura.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumQuestionRequest {
    private boolean isDeleted;
    private boolean isNew;
    private String question;
    private List<ForumQuestionImageRequest> forumQuestionImageRequest;

}
