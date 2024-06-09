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

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @PostMapping(ResourceManagementURI.LESSON + URIPrefix.CREATE)
    public AppResponse<String> createLesson(@RequestHeader("Authorization") String jwtToken,@RequestBody AppRequest<LessonRequest> request) {

        try {
            String s = lessonService.saveLesson(jwtToken , request.getData());
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PatchMapping(ResourceManagementURI.LESSON + URIPrefix.UPDATE)
    public AppResponse<String> updateLesson(@RequestHeader("Authorization") String jwtToken,@RequestBody AppRequest<LessonRequest> request, @PathVariable UUID id){
        try {
            String s = lessonService.updateLesson(jwtToken,request.getData(), id);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @DeleteMapping(ResourceManagementURI.LESSON + URIPrefix.DELETE)
    public AppResponse<String> deleteLesson(@RequestHeader("Authorization") String jwtToken,@RequestParam UUID id){
        try {
            String s = lessonService.deleteLesson(jwtToken,id);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.LESSON + URIPrefix.GET_ALL)
    public AppResponse<List<LessonResponseDto>> getAllLessons(@RequestHeader("Authorization") String jwtToken,@PathVariable UUID classId) {
        try {
            List<LessonResponseDto> response = this.lessonService.findAllLesson(jwtToken,classId);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.LESSON + URIPrefix.GET)
    public AppResponse<LessonResponseDto> getLesson(@RequestHeader("Authorization") String jwtToken,@RequestParam UUID id) {
        try {
            LessonResponseDto response = this.lessonService.findLesson(jwtToken,id);
            return AppResponse.ok(response);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}