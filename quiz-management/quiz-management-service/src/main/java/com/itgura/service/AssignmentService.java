package com.itgura.service;

import com.itgura.request.AssignmentSubmissionRequest;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.response.AssignmentSubmissionDTO;
import com.itgura.response.AssignmentSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface AssignmentService {
    public String createAssignment(CreateAssignmentRequest request);
    AssignmentResponse getAssignmentById(UUID id); // New method
    public void deleteAssignment(UUID id);
    public List<AssignmentSummaryDTO> getAssignmentsByClassIds(List<UUID> classIds) ;
    public boolean updatePublishedStatus(UUID id, Boolean isPublished);
    public boolean submitAssignmentAnswer(UUID assignmentId, UUID studentId, AssignmentSubmissionRequest request);
    public List<AssignmentSubmissionDTO> getAllSubmissions(UUID assignmentId);
}
