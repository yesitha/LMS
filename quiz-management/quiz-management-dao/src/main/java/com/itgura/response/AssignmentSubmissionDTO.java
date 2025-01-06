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
public class AssignmentSubmissionDTO {
    private UUID id;
    private UUID assignmentId;
    private UUID studentId;
    private String fileUrl; // File URL of the submission
    private Timestamp submittedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}