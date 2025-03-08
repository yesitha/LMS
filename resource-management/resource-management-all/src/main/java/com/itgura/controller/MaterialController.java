package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.request.MaterialRequest;
import com.itgura.request.SignedUrlRequest;
import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.service.MaterialService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @PostMapping( ResourceManagementURI.MATERIAL+URIPrefix.CREATE+ResourceManagementURI.SESSION_ID)
    public AppResponse<String> createMaterial(@PathVariable UUID sessionId, @RequestBody AppRequest<MaterialRequest> request) {
        try {
            String s = materialService.addMaterial(sessionId,request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PatchMapping( ResourceManagementURI.MATERIAL+URIPrefix.UPDATE+ResourceManagementURI.MATERIAL_ID)
    public AppResponse<String> updateMaterial(@PathVariable UUID materialId, @RequestBody AppRequest<MaterialRequest> request) {
        try {
            String s = materialService.updateMaterial(materialId,request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping( ResourceManagementURI.MATERIAL+URIPrefix.DELETE+ResourceManagementURI.MATERIAL_ID)
    public AppResponse<String> deleteMaterial(@PathVariable UUID materialId) {
        try {
            String s = materialService.deleteMaterial(materialId);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PostMapping (ResourceManagementURI.MATERIAL + URIPrefix.GET_VIDEO_Signed_Url)
   public AppResponse<String> getVideoMaterialSignedUrl(@RequestBody AppRequest<SignedUrlRequest> request) {
        try {
            String s = materialService.getVideoMaterialSignedUrl(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
   }
    @GetMapping (ResourceManagementURI.MATERIAL + ResourceManagementURI.MATERIAL_ID)
    public AppResponse<MaterialResponseDto> getMaterialById(@PathVariable UUID materialId) {
        try {
            MaterialResponseDto dto = materialService.getMaterialById(materialId);
            return AppResponse.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping (ResourceManagementURI.MATERIAL +URIPrefix.GET_ALL+ ResourceManagementURI.SESSION_ID)
    public AppResponse<List<MaterialResponseDto>> getAllMaterialBySessionId(@PathVariable UUID sessionId) {
        try {
            List<MaterialResponseDto> dto = materialService.getAllMaterialBySessionId(sessionId);
            return AppResponse.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
