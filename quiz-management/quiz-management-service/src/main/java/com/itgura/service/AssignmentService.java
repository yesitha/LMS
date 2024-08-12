package com.itgura.service;

import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;

import java.util.UUID;

public interface AssignmentService {
    public String createAssignment(CreateAssignmentRequest request);
    AssignmentResponse getAssignmentById(UUID id); // New method
}
