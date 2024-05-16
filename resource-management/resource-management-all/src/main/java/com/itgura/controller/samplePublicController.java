package com.itgura.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resource-management/public")
@RequiredArgsConstructor
public class samplePublicController {
    @GetMapping("/sample")
    public String sampleUser(){
        return "Sample";
    }
}
