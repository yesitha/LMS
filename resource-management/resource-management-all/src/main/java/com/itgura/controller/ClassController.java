package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.ClassRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.hasPermissionResponse;
import com.itgura.service.ClassService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


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
    @PostMapping(value = ResourceManagementURI.CLASS + ResourceManagementURI.CREATE,consumes = "multipart/form-data")
    public AppResponse<String> createClass(@RequestPart("request") AppRequest<ClassRequest> request,@RequestPart(value = "image", required = false) MultipartFile file) {

        try {

            String response = this.classService.create(request.getData(), file);
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
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.CLASS +URIPrefix.BY_ID)
    public AppResponse<ClassResponseDto> getClass(@PathVariable UUID id) {
        try {
            ClassResponseDto classById = this.classService.getClassById(id);
            return AppResponse.ok(classById);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    
    @PatchMapping(value=ResourceManagementURI.CLASS + URIPrefix.UPDATE + URIPrefix.ID,consumes = "multipart/form-data")
    public AppResponse<String> updateClass(@PathVariable("id") UUID id, @RequestPart("request") AppRequest<ClassRequest> request,@RequestPart(value = "image", required = false) MultipartFile file) {
        try {
            String update = this.classService.update(id, request.getData(), file);
            return AppResponse.ok(update);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
