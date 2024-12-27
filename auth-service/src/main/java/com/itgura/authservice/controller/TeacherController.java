package com.itgura.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/auth-service-protected/admin/teacher")
@RequiredArgsConstructor
public class TeacherController {

            @PostMapping("/testTeacher")
            public String seyHello() {
                return "Hello from Teacher Controller!";
            }
}
