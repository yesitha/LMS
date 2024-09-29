package com.itgura.config;

import com.itgura.util.DummyEntityManagerFactory;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class permissionConfig {
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        // Implement a dummy EntityManagerFactory
        return new DummyEntityManagerFactory();
    }
}
