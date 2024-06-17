package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.request.ClassRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.service.ClassService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping(ResourceManagementURI.CLASS + ResourceManagementURI.ALL)
    public AppResponse<List<ClassResponseDto>> getAllClasses() {

        try {
            List<ClassResponseDto> response = this.classService.getAllClasses();
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(ResourceManagementURI.CLASS + ResourceManagementURI.CREATE)
    public AppResponse<String> createClass(@RequestBody ClassRequest request) {

        try {
            String response = this.classService.create(request);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    //getClassFee

    @GetMapping(ResourceManagementURI.CLASS + ResourceManagementURI.GET_CLASS_FEE+URIPrefix.BY_ID)
    public AppResponse<Double> getClassFee(@PathVariable UUID id) {
        try {
            Double response = this.classService.getClassFee(id);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
