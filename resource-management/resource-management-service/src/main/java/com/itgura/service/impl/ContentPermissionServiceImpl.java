package com.itgura.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.request.addSessionToMonthRequest;
import com.itgura.request.hasPermissionRequest;
import com.itgura.response.hasPermissionResponse;
import com.itgura.service.ContentPermissionService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.entity.AClass;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.request.ClassRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.request.hasPermissionRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.response.hasPermissionResponse;
import com.itgura.service.ClassService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class ContentPermissionServiceImpl implements ContentPermissionService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Add ObjectMapper

    @Override
    @Transactional
    public List<hasPermissionResponse> hasPermissionForContentList(List<UUID> contentIds) throws ApplicationException, URISyntaxException {
        URI uri = new URI("http://lms-gateway/payment-service/hasPermission");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());

        // Create request body
        AppRequest<hasPermissionRequest> requestBody = new AppRequest<>();
        hasPermissionRequest data = new hasPermissionRequest();
        data.setContentIds(contentIds);
        requestBody.setData(data);

        // Create HttpEntity with body and headers
        HttpEntity<AppRequest<hasPermissionRequest>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<AppResponse> responseEntity = restTemplate.postForEntity(uri, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting permissions: response or data is null");
            }

            // Manually deserialize the data into List<hasPermissionResponse>
            List<hasPermissionResponse> hasPermissionResponses = objectMapper.convertValue(
                    response.getData(), new TypeReference<List<hasPermissionResponse>>() {}
            );

            return hasPermissionResponses;

        } catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String givePermissionForStudents(UUID classId, int year, int month, UUID contentId) throws ApplicationException, URISyntaxException {
        URI uri = new URI("http://lms-gateway/payment-service/addSessionToMonth");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());

        // Create request body
        AppRequest<addSessionToMonthRequest> requestBody = new AppRequest<>();
        hasPermissionRequest data = new hasPermissionRequest();
        addSessionToMonthRequest addSessionToMonthRequest = new addSessionToMonthRequest();
        addSessionToMonthRequest.setClassId(classId);
        addSessionToMonthRequest.setYear(year);
        addSessionToMonthRequest.setMonth(month);
        addSessionToMonthRequest.setSessionId(contentId);
        requestBody.setData(addSessionToMonthRequest);

        // Create HttpEntity with body and headers
        HttpEntity<AppRequest<addSessionToMonthRequest>> entity = new HttpEntity<>(requestBody, headers);
        try{
            ResponseEntity<AppResponse> responseEntity = restTemplate.postForEntity(uri, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting permissions: response or data is null");
            }
            return (String) response.getData();
        }catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String deleteSession(UUID contentId) throws ApplicationException, URISyntaxException {
        URI uri = new URI("http://lms-gateway/payment-service/deleteSession");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
        AppRequest<UUID> requestBody = new AppRequest<>();

        requestBody.setData(contentId);
        HttpEntity<AppRequest<UUID>> entity = new HttpEntity<>(requestBody, headers);
        try{
            ResponseEntity<AppResponse> responseEntity = restTemplate.postForEntity(uri, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting permissions: response or data is null");
            }
            return (String) response.getData();
        }catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }

    }
}

