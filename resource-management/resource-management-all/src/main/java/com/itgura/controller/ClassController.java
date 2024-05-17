package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.service.ClassService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
