package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.request.AnnouncementRequest;
import com.itgura.response.dto.AnnouncementResponseDto;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.service.AnnouncementService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping(ResourceManagementURI.ANNOUNCEMENT + ResourceManagementURI.ALL+ URIPrefix.CLASS_ID)
    public AppResponse<List<AnnouncementResponseDto>> getAllClasses(@PathVariable UUID classId) {

        try {
            List<AnnouncementResponseDto> response = this.announcementService.findAllAnnouncement(classId);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.ANNOUNCEMENT + URIPrefix.CREATE+ URIPrefix.CLASS_ID)
    public AppResponse<String> createAnnouncement(@RequestBody AnnouncementRequest request, @PathVariable UUID classId) {

        try {
            String response = this.announcementService.createAnnouncement(classId,request);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.ANNOUNCEMENT + URIPrefix.DELETE+ResourceManagementURI.ANNOUNCEMENT_ID)
    public AppResponse<String> deleteAnnouncement(@PathVariable UUID announcementId){
        try {
            String s = announcementService.deleteAnnouncement(announcementId);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PatchMapping(ResourceManagementURI.ANNOUNCEMENT + URIPrefix.UPDATE+ ResourceManagementURI.ANNOUNCEMENT_ID)
    public AppResponse<String> updateAnnouncement(@RequestBody AnnouncementRequest request, @PathVariable UUID announcementId){
        try {
            String s = announcementService.updateAnnouncement(announcementId,request);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.ANNOUNCEMENT +URIPrefix.ID)
    public AppResponse<AnnouncementResponseDto> getAnnouncementById(@PathVariable UUID id) {
        try {
            AnnouncementResponseDto response = this.announcementService.findAnnouncementById(id);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
