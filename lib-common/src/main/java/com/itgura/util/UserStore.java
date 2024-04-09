package com.itgura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;


@Component
@RequestScope
@Getter
@Setter
public class UserStore {

    private Long userId;

    public UUID getUserId() throws JsonProcessingException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(7);
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        byte[] decodedPayload = Base64.getUrlDecoder().decode(parts[1]);
        String payload = new String(decodedPayload, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payloadJson = objectMapper.readTree(payload);
        String userIdString = payloadJson.get("UserId").asText();

        return UUID.fromString(userIdString);

    }


//    @PostConstruct
//    public void setUserData(){
////        if(propertiesConfig.isSecurityEnabled()) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            String userId = jwt.getClaimAsString("UserId");
//
//        } else {
//            // Handle the case where the principal is not a Jwt
//        }
//    }
}

