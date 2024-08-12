package com.itgura.service;

import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.response.AssignmentSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface AssignmentService {
    public String createAssignment(CreateAssignmentRequest request);
    AssignmentResponse getAssignmentById(UUID id); // New method
    public void deleteAssignment(UUID id);
    public List<AssignmentSummaryDTO> getAssignmentsByClassIds(List<UUID> classIds) ;
    public boolean updatePublishedStatus(UUID id, Boolean isPublished);
}
