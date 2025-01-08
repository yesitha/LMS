package com.itgura.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/v1/auth-service-protected/student")
@RequiredArgsConstructor


public class StudentController {

        @PostMapping("/testStudent")
        public String seyHello() {
            return "Hello from Student Controller!";
        }



}
