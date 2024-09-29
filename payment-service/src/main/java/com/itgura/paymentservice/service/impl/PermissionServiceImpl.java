package com.itgura.paymentservice.service.impl;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.paymentservice.dto.request.hasPermissionRequest;
import com.itgura.paymentservice.dto.response.hasPermissionResponse;
import com.itgura.paymentservice.entity.StudentTransactionContent;
import com.itgura.paymentservice.repository.StudentTransactionContentRepository;
import com.itgura.paymentservice.service.PermissionService;
import com.itgura.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.UUID;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;



    @Autowired
    private StudentTransactionContentRepository studentTransactionContentRepository;




    @Override
    public List<hasPermissionResponse> hasPermission(hasPermissionRequest data) throws ApplicationException {

        String authorizationHeader = UserUtil.extractToken();
        List<hasPermissionResponse> response = new ArrayList<>();


        if (authorizationHeader != null) {
            try {
                String token = authorizationHeader;
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY) // Replace with your actual secret key
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");
                List<String> authorities = roles.stream()
                        .map(roleMap -> roleMap.get("authority"))
                        .toList();
                List<UUID> contentIds = data.getContentIds();
                System.out.println("contentIds = " + contentIds);
                for (UUID contentId : contentIds) {
                    if (authorities.contains("ADMIN") || authorities.contains("TEACHER")) {
                        response.add(new hasPermissionResponse(contentId, true));
                    } else if (authorities.contains("STUDENT")) {
//                        response.add(new hasPermissionResponse(contentId, (studentTransactionContentRepository.existsByStudentIdAndContentId(email, contentId)|| hasPermissionForRelevantSession(email,contentId))));
                        response.add(new hasPermissionResponse(contentId, (studentTransactionContentRepository.existsByStudentIdAndContentId(email, contentId)))); // only checking has access to particular session - If it has access to session all the content can access in it
                    } else {
                        response.add(new hasPermissionResponse(contentId, false));
                    }
                }
                System.out.println("response = " + response);
                return response;

            } catch (Exception e) {
                System.out.println(e);
                return null;

            }

        }


        return null;
    }

    @Override
    public List<String> getEmailsHasAccessToSession(String sessionId) {
        List<String> emails = new ArrayList<>();
        List<StudentTransactionContent> studentTransactionContents = studentTransactionContentRepository.findByContentId(UUID.fromString(sessionId));
        if(!studentTransactionContents.isEmpty()) {

            for (StudentTransactionContent studentTransactionContent : studentTransactionContents) {
                emails.add(studentTransactionContent.getStudentEmail());
            }
        }
        return emails;
    }


//    private boolean hasPermissionForRelevantSession(String email, UUID contentId) throws ApplicationException {
//       //get session id for relevant content id
//        String url = "http://lms-gateway/resource-management/resource-management/session/getSessionIdByContentId/"+contentId;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<AppResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, AppResponse.class);
//            AppResponse response = responseEntity.getBody();
//
//            if (response == null || response.getData() == null) {
//                throw new ApplicationException("Error while getting sessions in month: response or data is null");
//            }
//
//            System.out.println("Sessions for Month "+response.getData());
//
//            return (List<UUID>) response.getData();
//
//        } catch (HttpClientErrorException.Forbidden e) {
//            throw new ApplicationException("Access is forbidden: " + e.getMessage());
//        } catch (HttpClientErrorException e) {
//            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
//        } catch (Exception e) {
//            throw new ApplicationException("Server error: " + e.getMessage());
//        }
//    }
}
