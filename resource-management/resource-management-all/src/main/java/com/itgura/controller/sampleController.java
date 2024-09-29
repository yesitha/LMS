package com.itgura.controller;

import com.itgura.request.dto.PermissionRevokeDto;
import com.itgura.util.rabbitMQMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource-management")
@RequiredArgsConstructor
public class sampleController {

    @Autowired
    private rabbitMQMessageProducer producer;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/sampleAdmin")
    public String sampleAdmin(){
        return "Sample";
    }
    @GetMapping("/sampleUser")
    public String sampleUser(){

        PermissionRevokeDto permissionRevokeDto = new PermissionRevokeDto();
        permissionRevokeDto.setVideoUrl("videoUrl");
        permissionRevokeDto.setEmails(List.of(new String[]{"email1", "email2"}));
        producer.publish(permissionRevokeDto,"internal.exchange","permission.revoke.routing-key");

        return "Sample";
    }

}
