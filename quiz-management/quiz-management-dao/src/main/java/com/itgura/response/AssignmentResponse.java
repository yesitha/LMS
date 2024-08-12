package com.itgura.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentResponse {
    private UUID id;
    private String title;
    private String description;
    private List<UUID> fileUrl;
    private Timestamp deadline;
    private Boolean isPublished;
    private List<UUID> classIds;
}
