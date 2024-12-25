package com.itgura.paymentservice.service.impl;

import com.itgura.exception.ApplicationException;
import com.itgura.paymentservice.dto.request.hasPermissionRequest;
import com.itgura.paymentservice.dto.response.hasPermissionResponse;
import com.itgura.paymentservice.repository.StudentTransactionContentRepository;
import com.itgura.paymentservice.service.PermissionService;
import com.itgura.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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
                        response.add(new hasPermissionResponse(contentId, studentTransactionContentRepository.existsByStudentIdAndContentId(email, contentId)));
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
}
