package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.AssignmentSubmissionRequest;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.response.AssignmentSubmissionDTO;
import com.itgura.response.AssignmentSummaryDTO;
import com.itgura.service.AssignmentService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.QUIZ_SERVICE)
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @PostMapping(ResourceManagementURI.ASSIGNMENT + ResourceManagementURI.CREATE)
    public AppResponse<String> createAssignment(@RequestBody AppRequest<CreateAssignmentRequest> request) {
        try {
            String response = this.assignmentService.createAssignment(request.getData());
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.ASSIGNMENT + ResourceManagementURI.GET_BY_ID)
    public AppResponse<AssignmentResponse> getAssignmentById(@RequestParam("id") UUID id) {
        try {
            AssignmentResponse assignment = assignmentService.getAssignmentById(id);
            if (assignment != null) {
                return AppResponse.ok(assignment);
            } else {
                return AppResponse.error(null, "Assignment not found", "Not Found", "404", "");
            }
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @DeleteMapping(ResourceManagementURI.ASSIGNMENT + "/{id}")
    public AppResponse<String> deleteAssignment(@PathVariable UUID id) {
        try {
            this.assignmentService.deleteAssignment(id);
            return AppResponse.ok("Assignment deleted successfully");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


    @GetMapping(ResourceManagementURI.ASSIGNMENT)
    public AppResponse<List<AssignmentSummaryDTO>> getAssignmentsByClassIds(@RequestParam List<UUID> classIds) {
        try {
            List<AssignmentSummaryDTO> assignments = assignmentService.getAssignmentsByClassIds(classIds);
            return AppResponse.ok(assignments);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @PatchMapping(ResourceManagementURI.ASSIGNMENT+ResourceManagementURI.ID+ResourceManagementURI.PUBLISH)
    public ResponseEntity<String> updateAssignmentPublishedStatus(@PathVariable UUID id, @RequestParam Boolean isPublished) {
        try {
            boolean updated = assignmentService.updatePublishedStatus(id, isPublished);
            if (updated) {
                return ResponseEntity.ok("Assignment published status updated successfully.");
            } else {
                return ResponseEntity.status(404).body("Assignment not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating assignment published status: " + e.getMessage());
        }
    }
    @PostMapping(ResourceManagementURI.ASSIGNMENT + ResourceManagementURI.ANSWERTOASSIGNMENT + ResourceManagementURI.ASSIGNMENT_ID+ResourceManagementURI.STUDENT_ID)
    public AppResponse<Boolean> answerToAssignment(@PathVariable UUID assignmentId,@PathVariable UUID studentId, @RequestBody AppRequest<AssignmentSubmissionRequest> request) {
        try {
            Boolean b = this.assignmentService.submitAssignmentAnswer(assignmentId, studentId, request.getData());
            return AppResponse.ok(b);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @GetMapping(ResourceManagementURI.ASSIGNMENT +ResourceManagementURI.ASSIGNMENT_ID+ResourceManagementURI.GET_ALL_SUBMISSIONS)
    public AppResponse<List<AssignmentSubmissionDTO> > getAllAnswers(@PathVariable UUID assignmentId) {
        try {
            List<AssignmentSubmissionDTO> allSubmissions = this.assignmentService.getAllSubmissions(assignmentId);
            return AppResponse.ok(allSubmissions);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

}
