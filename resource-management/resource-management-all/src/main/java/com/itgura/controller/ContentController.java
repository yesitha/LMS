package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.entity.Content;
import com.itgura.entity.Tag;
import com.itgura.response.dto.TagResponseDto;
import com.itgura.service.ContentService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)



public class ContentController {
    @Autowired
    private ContentService contentService;



    @PatchMapping(ResourceManagementURI.CONTENT + URIPrefix.UPDATE_TAGS + URIPathVariable.CONTENT_ID)
    public AppResponse<String> updateTags(@PathVariable UUID contentId, @RequestBody List<String> tags) {
        try {
            String response = this.contentService.updateTags(contentId, tags);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(ResourceManagementURI.TAGS + ResourceManagementURI.ALL)
    public AppResponse<List<TagResponseDto>> getAllTags() {
        try {
            List<TagResponseDto> response = this.contentService.getAllTags();
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PatchMapping(ResourceManagementURI.CONTENT + URIPrefix.UPDATE_ACCESS_TIME_DURATION + URIPathVariable.CONTENT_ID)
    public AppResponse<String> updateAccessTimeDuration(@PathVariable UUID contentId, @RequestBody Integer accessTimeDuration) {
        try {
            String response = this.contentService.updateAccessTimeDuration(contentId, accessTimeDuration);
            return AppResponse.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(ResourceManagementURI.CONTENT + URIPrefix.GET_ACCESS_TIME_DURATION + URIPathVariable.CONTENT_ID)
    public AppResponse<Integer> getAccessTimeDuration(@PathVariable UUID contentId) {
        try {
            Integer response = this.contentService.getAccessTimeDuration(contentId);
            return AppResponse.ok(response);
        }catch (NotFoundException e){
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Not Found", "404", "");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(ResourceManagementURI.CONTENT + URIPrefix.GET_PRICE + URIPathVariable.CONTENT_ID)
    public AppResponse<Double> getPrice(@PathVariable UUID contentId) {
        try {
            Double response = this.contentService.getPrice(contentId);
            return AppResponse.ok(response);
        }catch (NotFoundException e){
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Not Found", "404", "");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
