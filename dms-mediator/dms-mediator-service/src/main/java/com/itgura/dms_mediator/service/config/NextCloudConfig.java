package com.itgura.dms_mediator.service.config;



import com.itgura.dms_mediator.util.DummyEntityManagerFactory;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration


public class NextCloudConfig {
    @Value("${nextcloud.base.url}")
    private String nextcloudBaseUrl;

    @Value("${nextcloud.uploadUrl}")
    private String uploadUrlTemplate;

    @Value("${nextcloud.downloadUrl}")
    private String downloadUrlTemplate;

    @Value("${nextcloud.deleteUrl}")
    private String deleteUrlTemplate;


    @Value("${nextcloud.username}")
    private String username;

    @Value("${nextcloud.password}")
    private String password;

    @Value("${nextcloud.uploadRelativePath}")
    private String uploadRelativePath;



    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .basicAuthentication(username, password)
                .build();
    }

    public String buildUploadUrl(String path) {
        return nextcloudBaseUrl + uploadUrlTemplate
                .replace("{username}", username)
                .replace("{path}", path.startsWith("/") ? path.substring(1) : path);
    }

    public String buildDownloadUrl(String path, String filename) {
        return nextcloudBaseUrl + downloadUrlTemplate
                .replace("{username}", username)
                .replace("{path}", path.startsWith("/") ? path.substring(1) : path)
                .replace("{filename}", filename);
    }

    public String buildDeleteUrl(String path, String filename) {
        return nextcloudBaseUrl + deleteUrlTemplate
                .replace("{username}", username)
                .replace("{path}", path.startsWith("/") ? path.substring(1) : path)
                .replace("{filename}", filename);
    }
    public String getNextcloudBaseUrl() {
        return nextcloudBaseUrl;
    }

    public String getUploadUrlTemplate() {
        return uploadUrlTemplate;
    }

    public String getDownloadUrlTemplate() {
        return downloadUrlTemplate;
    }

    public String getDeleteUrlTemplate() {
        return deleteUrlTemplate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUploadRelativePath() {
        return uploadRelativePath;
    }
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        // Implement a dummy EntityManagerFactory
        return new DummyEntityManagerFactory();
    }

}
