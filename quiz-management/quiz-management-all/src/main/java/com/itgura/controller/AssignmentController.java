package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.CreateAssignmentRequest;
import com.itgura.response.AssignmentResponse;
import com.itgura.service.AssignmentService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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

}
