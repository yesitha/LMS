package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Getter
@Setter
public class UserUtil {


    public static String getUserEmail(String jwtToken, String SECRET_KEY) throws BadRequestRuntimeException {
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            try {
                String token = jwtToken.substring(7);
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY) // Replace with your actual secret key
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();

                return email;


            } catch (Exception e) {
                throw new BadRequestRuntimeException("Invalid Token");

            }
        }

        return null;

    }

    public static List<String> getUserRoles(String jwtToken, String SECRET_KEY) throws BadRequestRuntimeException {
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            try {
                String token = jwtToken.substring(7);
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY) // Replace with your actual secret key
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");
                List<String> authorities = roles.stream()
                        .map(roleMap -> roleMap.get("authority"))
                        .toList();

                return authorities;


            } catch (Exception e) {
                throw new BadRequestRuntimeException("Invalid Token");

            }
        }
        return null;
    }

    public static String extractToken() throws ApplicationException {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    return authorizationHeader.substring(7);  // Extracts token after "Bearer "
                }
            }
            throw new BadRequestRuntimeException("Token not found");
        } catch (Exception e) {
            throw new ApplicationException("Error extracting token");
        }
    }
}
