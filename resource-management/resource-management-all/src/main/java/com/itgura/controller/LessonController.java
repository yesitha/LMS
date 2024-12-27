package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.LessonRequest;
import com.itgura.response.dto.LessonResponseDto;
import com.itgura.service.LessonService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @PostMapping(value = ResourceManagementURI.LESSON + URIPrefix.CREATE, consumes = "multipart/form-data")
    public AppResponse<String> createLesson(@RequestPart("request")AppRequest<LessonRequest> request,@RequestPart (value = "image", required = false) MultipartFile file) {

        try {
            String s = lessonService.saveLesson( request.getData(), file);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PatchMapping(value=ResourceManagementURI.LESSON + URIPrefix.UPDATE+ URIPrefix.ID, consumes = "multipart/form-data")
    public AppResponse<String> updateLesson(@RequestPart("request") AppRequest<LessonRequest> request, @PathVariable UUID id,@RequestPart (value = "image", required = false) MultipartFile file){
        try {
            String s = lessonService.updateLesson(request.getData(), id, file);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.LESSON + URIPrefix.DELETE+ URIPrefix.ID)
    public AppResponse<String> deleteLesson(@RequestParam UUID id){
        try {
            String s = lessonService.deleteLesson(id);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.LESSON + URIPrefix.GET_ALL+ URIPrefix.CLASS_ID)
    public AppResponse<List<LessonResponseDto>> getAllLessons(@PathVariable UUID classId) {
        try {
            List<LessonResponseDto> response = this.lessonService.findAllLesson(classId);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.LESSON + URIPrefix.GET)
    public AppResponse<LessonResponseDto> getLesson(@RequestParam UUID id) {
        try {
            LessonResponseDto response = this.lessonService.findLesson(id);
            return AppResponse.ok(response);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
