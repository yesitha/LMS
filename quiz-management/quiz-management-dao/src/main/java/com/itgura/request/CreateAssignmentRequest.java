package com.itgura.request;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateAssignmentRequest {

    private String title;
    private String description;
    private List<UUID> fileUrls; // Updated to handle a list of file URLs
    private Timestamp deadline;
    private Boolean isPublished;
    private Long duration;
    private UUID createdBy;
    private List<UUID> classIds;
}

