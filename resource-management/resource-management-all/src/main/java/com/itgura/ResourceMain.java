package com.itgura;
import com.itgura.request.dto.PermissionGrantDto;
import com.itgura.request.dto.PermissionRevokeDto;
import com.itgura.util.BootMain;
import com.itgura.util.rabbitMQMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication



public class ResourceMain {


    public static void main(String[] args) {


        BootMain.main(args);
        SpringApplication.run(ResourceMain.class);
    }

    @Bean
    CommandLineRunner init(rabbitMQMessageProducer producer) {

        PermissionGrantDto permissionGrantDto = new PermissionGrantDto();
        permissionGrantDto.setVideoUrl("https://www.youtube.com/watch?v=123456");
        permissionGrantDto.setEmails(List.of("email1", "email2"));
        return args -> {
            producer.publish(permissionGrantDto, "internal.exchange", "permission.grant.routing-key");
        };

    }



}