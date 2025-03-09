package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.MaterialRequest;
import com.itgura.request.SignedUrlRequest;
import com.itgura.request.VideoUploadRequest;
import com.itgura.response.dto.PreSignedUrlToUploadVideoResponseDto;
import com.itgura.service.MaterialService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
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

    @PostMapping (ResourceManagementURI.MATERIAL+URIPrefix.GET_VIDEO_Signed_Url)
   public AppResponse<String> getVideoMaterialSignedUrl(@RequestBody AppRequest<SignedUrlRequest> request) {
        try {
            String s = materialService.getVideoMaterialSignedUrl(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
   }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @PostMapping (ResourceManagementURI.MATERIAL+URIPrefix.GET_Pre_Signed_Url_To_Upload_Video)
    public AppResponse<PreSignedUrlToUploadVideoResponseDto> getPreSignedUrlToUploadVideo(@RequestBody AppRequest<VideoUploadRequest> request) {
        try {
            PreSignedUrlToUploadVideoResponseDto responseDto = materialService.getPreSignedUrlToUploadVideo(request.getData());
            return AppResponse.ok(responseDto);
        } catch (ValueNotExistException e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        }catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TEACHER')")
    @PostMapping (ResourceManagementURI.MATERIAL+URIPrefix.MARKED_VIDEO_AS_UPLOADED+ResourceManagementURI.MATERIAL_ID)
    public AppResponse<String> markedVideoAsUploaded( @PathVariable UUID materialId) {
        try {
            String s = materialService.markedVideoAsUploaded(materialId);
            return AppResponse.ok(s);

        } catch (ValueNotExistException ex) {
            ex.printStackTrace();
            return AppResponse.error(null, ex.getMessage(), "Value Not Found", "404", "");

        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }






}
