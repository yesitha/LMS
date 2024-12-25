package com.itgura.service.impl;

import com.itgura.entity.Assignment;
import com.itgura.repository.AssignmentRepository;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.response.AssignmentSummaryDTO;
import com.itgura.service.AssignmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Override
    @Transactional
    public String createAssignment(CreateAssignmentRequest request) {
        try {
            // Create and set up the Assignment entity
            Assignment assignment = new Assignment();
            assignment.setTitle(request.getTitle());
            assignment.setDescription(request.getDescription());
            assignment.setFileUrls(request.getFileUrls()); // Updated to handle list of file URLs
            assignment.setDeadline(request.getDeadline());
            assignment.setDuration(request.getDuration());
            assignment.setIsPublished(request.getIsPublished());
            assignment.setCreatedBy(request.getCreatedBy());
            assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            assignment.setClassIds(request.getClassIds());

            // Save the assignment and return the assignment ID
            Assignment savedAssignment = assignmentRepository.save(assignment);
            return "Assignment created successfully with ID: " + savedAssignment.getId().toString();
        } catch (Exception e) {
            return "Error creating assignment: " + e.getMessage();
        }
    }
    @Override
    @Transactional
    public AssignmentResponse getAssignmentById(UUID id) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);
        if (assignmentOptional.isPresent()) {
            Assignment assignment = assignmentOptional.get();

            // Map to DTO
            return new AssignmentResponse(
                    assignment.getId(),
                    assignment.getTitle(),
                    assignment.getDescription(),
                    assignment.getFileUrls(), // Updated to handle list of file URLs
                    assignment.getDeadline(),
                    assignment.getIsPublished(),
                    assignment.getClassIds()
            );
        } else {
            throw new RuntimeException("Assignment not found");
        }
    }

    @Override
    @Transactional
    public void deleteAssignment(UUID id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Assignment not found with ID: " + id);
        }
    }
    @Override
    @Transactional
    public List<AssignmentSummaryDTO> getAssignmentsByClassIds(List<UUID> classIds) {
        List<Assignment> assignments = assignmentRepository.findByClassIdsIn(classIds);
        return assignments.stream().map(assignment -> new AssignmentSummaryDTO(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDeadline(),
                assignment.getDuration()
        )).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updatePublishedStatus(UUID id, Boolean isPublished) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
        if (optionalAssignment.isPresent()) {
            Assignment assignment = optionalAssignment.get();
            assignment.setIsPublished(isPublished);
            assignmentRepository.save(assignment);
            return true;
        }
        return false;
    }
}
