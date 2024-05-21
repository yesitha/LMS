package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itgura.exception.BadRequestRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Getter
@Setter
public class UserUtil {


    public String getUserEmail(String jwtToken, String SECRET_KEY) throws BadRequestRuntimeException {
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

    public List<String> getUserRoles(String jwtToken, String SECRET_KEY) throws BadRequestRuntimeException {
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
}
