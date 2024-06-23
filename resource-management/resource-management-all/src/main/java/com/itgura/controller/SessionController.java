package com.itgura.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.SessionRequest;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.service.SessionService;
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
public class SessionController {
    @Autowired
    private SessionService sessionService;
    @GetMapping(ResourceManagementURI.SESSION + URIPrefix.GET_ALL+URIPrefix.LESSON_ID)
    public AppResponse<List<SessionResponseDto>> findAllSessions( @PathVariable UUID lessonId) {
        try {
            List<SessionResponseDto> s = sessionService.findAllSession(lessonId);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.SESSION + URIPrefix.GET_ALL+ResourceManagementURI.INMONTHANDCLASS+URIPrefix.CLASS_ID+URIPrefix.YEAR+URIPrefix.MONTH)
    public AppResponse<List<UUID>> findAllSessionsInMonth(@PathVariable UUID classId, @PathVariable int year, @PathVariable int month) {
        try {
            List<UUID> s = sessionService.findAllSessionsInMonth(classId,month,year);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.SESSION + URIPrefix.CREATE+URIPrefix.LESSON_ID)
    public AppResponse<String> createSession(@PathVariable UUID lessonId, @RequestBody AppRequest<SessionRequest> request) {
        try {
            String s = sessionService.createSession(lessonId,request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @GetMapping(ResourceManagementURI.SESSION + URIPrefix.GET+URIPrefix.SESSION_ID)
    public AppResponse<SessionResponseDto> getSession(@PathVariable UUID sessionId) {
        try {
            SessionResponseDto s = sessionService.findSessionById(sessionId);
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PatchMapping
    (ResourceManagementURI.SESSION + URIPrefix.UPDATE+URIPrefix.SESSION_ID)
    public AppResponse<String> updateSession(@PathVariable UUID sessionId, @RequestBody AppRequest<SessionRequest> request) {
        try {
            String s = sessionService.updateSession(sessionId,request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

}
