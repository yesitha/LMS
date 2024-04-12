package com.itgura.dms_mediator.service.config;



import com.itgura.dms_mediator.util.DummyEntityManagerFactory;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration


public class AlfrescoConfig {
    @Value("${alfresco.base.url}")
    private String alfrescoBaseUrl;

    @Value("${alfresco.uploadUrl}")
    private String uploadUrl;

    @Value("${alfresco.downloadUrl}")
    private String downloadUrl;

    @Value("${alfresco.deleteUrl}")
    private String deleteUrl;


    @Value("${alfresco.username}")
    private String username;

    @Value("${alfresco.password}")
    private String password;

    @Value("${alfresco.uploadRelativePath}")
    private String uploadRelativePath;

    public String getUploadRelativePath() {
        return uploadRelativePath;
    }

    public void setUploadRelativePath(String uploadRelativePath) {
        this.uploadRelativePath = uploadRelativePath;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .basicAuthentication(username, password)
                .build();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public String getAlfrescoBaseUrl() {
        return alfrescoBaseUrl;
    }

    public void setAlfrescoBaseUrl(String alfrescoBaseUrl) {
        this.alfrescoBaseUrl = alfrescoBaseUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        // Implement a dummy EntityManagerFactory
        return new DummyEntityManagerFactory();
    }
}
