package com.itgura.lmsgateway.util;


import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "f2b21eeadc7f3693dbc373dca5f49400293d722eb955353c11250b9367cd1635";


    public void validateToken(final String token) throws NoSuchAlgorithmException {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

    }


}
