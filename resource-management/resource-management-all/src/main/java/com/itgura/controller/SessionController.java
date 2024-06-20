package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
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
    @GetMapping(ResourceManagementURI.SESSION + URIPrefix.GET_ALL)
    public AppResponse<List<SessionResponseDto>> getAllSessions( @PathVariable UUID lessonId) {
        try {
            List<SessionResponseDto> s = sessionService.findAllSession(lessonId);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.SESSION + URIPrefix.GET_ALL+ResourceManagementURI.INMONTHANDCLASS)
    public AppResponse<List<UUID>> findAllSessionsInMonth(@PathVariable UUID classId, @PathVariable int year, @PathVariable int month) {
        try {
            List<UUID> s = sessionService.findAllSessionsInMonth(classId,year,month);
            return AppResponse.ok(s);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

}
