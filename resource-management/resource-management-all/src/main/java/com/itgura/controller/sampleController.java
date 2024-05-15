package com.itgura.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resource-management")
@RequiredArgsConstructor
public class sampleController {
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/sampleAdmin")
    public String sampleAdmin(){
        return "Sample";
    }
    @GetMapping("/sampleUser")
    public String sampleUser(){
        return "Sample";
    }

}
