package com.itgura;

import com.itgura.util.BootMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QuizMain {
    public static void main(String[] args) {

        BootMain.main(args);
        SpringApplication.run(QuizMain.class);
    }
}
