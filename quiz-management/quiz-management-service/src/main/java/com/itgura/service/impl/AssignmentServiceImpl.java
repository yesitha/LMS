package com.itgura.service.impl;

import com.itgura.entity.Assignment;
import com.itgura.entity.AssignmentSubmission;
import com.itgura.repository.AssignmentRepository;
import com.itgura.repository.AssignmentSubmissionRepository;
import com.itgura.request.AssignmentSubmissionRequest;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.response.AssignmentSubmissionDTO;
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
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Override
    @Transactional
    public boolean submitAssignmentAnswer(UUID assignmentId, UUID studentId, AssignmentSubmissionRequest request) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
        if (optionalAssignment.isEmpty()) {
            throw new RuntimeException("Assignment not found with id: " + assignmentId);
        }
        Assignment assignment = optionalAssignment.get();
        if (!assignment.getIsPublished()) {
            throw new RuntimeException("Assignment is not published.");
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (assignment.getDeadline() != null && now.after(assignment.getDeadline())) {
            throw new RuntimeException("Submission deadline has passed.");
        }
        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudentId(studentId);
        submission.setFileUrl(request.getFileUrl());
        submission.setCreatedBy(studentId);
        submission.setCreatedAt(now);
        submission.setSubmittedAt(now);
        assignmentSubmissionRepository.save(submission);
        return true;
    }
    @Override
    @Transactional
    public List<AssignmentSubmissionDTO> getAllSubmissions(UUID assignmentId) {
        // Fetch submissions by assignmentId from the repository
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByAssignmentId(assignmentId);
        // Convert entities to DTOs
        return submissions.stream().map(submission -> {
            AssignmentSubmissionDTO dto = new AssignmentSubmissionDTO();
            dto.setId(submission.getId());
            dto.setAssignmentId(submission.getAssignment().getId());
            dto.setStudentId(submission.getStudentId());
            dto.setFileUrl(submission.getFileUrl().toString());
            dto.setSubmittedAt(submission.getSubmittedAt());
            dto.setCreatedAt(submission.getCreatedAt());
            dto.setUpdatedAt(submission.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());
    }
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
