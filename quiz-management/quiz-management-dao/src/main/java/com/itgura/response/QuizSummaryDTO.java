package com.itgura.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSummaryDTO {
    private UUID id;
    private String title;
    private String description;
    private Timestamp startTime;
    private Long duration; // Duration in minutes
    private Timestamp deadline;
}

