package com.itgura.paymentservice.controller;
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
@RequestMapping("/api/v1/payment-service")
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private final PermissionService permissionService;



    @PostMapping("/hasPermission")
    public Boolean hasPermission(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                 @Valid @RequestBody AppRequest<hasPermissionRequest> request) {

       try{


                return permissionService.hasPermission(request.getData(),authorizationHeader);
            } catch (Exception e) {

                return false;
            }
        }


}
