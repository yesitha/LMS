package com.itgura.util;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface JwtService {
    public String extractUserName(String token);
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver);
    public String generateToken(UserDetails userDetails);
    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails);
    public Boolean isTokenValid(String token, UserDetails userDetails);
    public String generateRefresh(Map<String, Objects> extraClaims, UserDetails userDetails);

}
