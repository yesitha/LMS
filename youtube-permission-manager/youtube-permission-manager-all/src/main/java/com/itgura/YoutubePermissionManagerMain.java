package com.itgura;



import com.itgura.service.YoutubePermissionService;
import com.itgura.util.BootMain;
import com.itgura.util.rabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient
public class YoutubePermissionManagerMain {



    public static void main(String[] args) {
        BootMain.main(args);
        SpringApplication.run(YoutubePermissionManagerMain.class, args);
    }






}
