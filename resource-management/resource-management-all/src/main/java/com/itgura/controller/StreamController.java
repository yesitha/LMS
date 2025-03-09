package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
import com.itgura.response.dto.StreamResponseDto;
import com.itgura.service.UserStreamService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class StreamController {
    @Autowired
    private UserStreamService userStreamService;

    @PostMapping(value = ResourceManagementURI.STREAM + URIPrefix.CREATE)
    public AppResponse<String> createStream(@RequestParam String stream) {
        try {
            String response = userStreamService.createStream(stream);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @DeleteMapping(value = ResourceManagementURI.STREAM + URIPrefix.DELETE + URIPrefix.ID)
    public AppResponse<String> deleteStream(@PathVariable UUID id) {
        try {
            String response = userStreamService.deleteStream(id);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @PatchMapping(value = ResourceManagementURI.STREAM + URIPrefix.UPDATE + URIPrefix.ID)
    public AppResponse<String> updateStream(@PathVariable UUID id, @RequestParam String stream) {
        try {
            String response = userStreamService.updateStream(id, stream);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(value = ResourceManagementURI.STREAM + URIPrefix.GET + URIPrefix.ID)
    public AppResponse<StreamResponseDto> getStreamById(@PathVariable UUID id) {
        try {
            StreamResponseDto response = userStreamService.getStreamById(id);
            return AppResponse.ok(response);
        } catch (ValueNotExistException e) {
            return AppResponse.error(null, e.getMessage(), "Value Not Found", "404", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }

    @GetMapping(value = ResourceManagementURI.STREAM + URIPrefix.GET_ALL)
    public AppResponse<List<StreamResponseDto>> getAllStreams() {
        try {
            List<StreamResponseDto> response = userStreamService.getAllStreams();
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
