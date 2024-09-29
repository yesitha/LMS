package com.itgura.paymentservice.controller;
import com.itgura.dto.AppResponse;
import com.itgura.paymentservice.dto.response.hasPermissionResponse;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import io.jsonwebtoken.Jwts;
import com.itgura.dto.AppRequest;
import com.itgura.paymentservice.dto.request.hasPermissionRequest;
import com.itgura.paymentservice.service.PermissionService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.PAYMENT_SERVICE)
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private final PermissionService permissionService;

 // can only be used to check has access to session and if purchased wise content then it can check
    @PostMapping("/hasPermission")
    public AppResponse<List<hasPermissionResponse>> hasPermission(@Valid @RequestBody AppRequest<hasPermissionRequest> request) {

       try{

           System.out.println("request.getData() = " + request.getData());
                return AppResponse.ok(permissionService.hasPermission(request.getData()));
            } catch (Exception e) {

                return null;
            }
        }


    @PostMapping("/permission/getEmailsHasAccessToSession"+URIPathVariable.SESSION_ID)
    public AppResponse<List<String>> getEmailsHasAccessToSession(@PathVariable String sessionId) {
        try{
            return AppResponse.ok(permissionService.getEmailsHasAccessToSession(sessionId));
        } catch (Exception e) {
            return null;
        }
    }
}
