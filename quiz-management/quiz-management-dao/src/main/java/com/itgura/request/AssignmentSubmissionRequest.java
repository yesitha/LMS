package com.itgura.request;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AssignmentSubmissionRequest {
    private String fileUrl;
}