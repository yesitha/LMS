package com.itgura.lmsgateway.util;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;


    public void validateToken(final String token) throws NoSuchAlgorithmException {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

    }


}
