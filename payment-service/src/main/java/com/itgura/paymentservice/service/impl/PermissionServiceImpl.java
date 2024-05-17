package com.itgura.paymentservice.service.impl;

import com.itgura.paymentservice.dto.request.hasPermissionRequest;
import com.itgura.paymentservice.repository.StudentTransactionContentRepository;
import com.itgura.paymentservice.service.PermissionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;



    @Autowired
    private StudentTransactionContentRepository studentTransactionContentRepository;

    @Override
    public Boolean hasPermission(hasPermissionRequest data, String authorizationHeader) {


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7);
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


                if (authorities.contains("ADMIN") || authorities.contains("TEACHER")) {
                    return true;
                } else if (authorities.contains("STUDENT")) {
                    return studentTransactionContentRepository.existsByStudentIdAndContentId(email, data.getContentId());
                } else {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }

        }


        return false;
    }
}
