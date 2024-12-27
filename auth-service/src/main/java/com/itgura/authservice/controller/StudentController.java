package com.itgura.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/auth-service-protected/student")
@RequiredArgsConstructor


public class StudentController {

        @PostMapping("/testStudent")
        public String seyHello() {
            return "Hello from Student Controller!";
        }



}
