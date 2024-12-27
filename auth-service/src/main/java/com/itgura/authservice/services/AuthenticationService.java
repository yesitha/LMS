package com.itgura.authservice.services;

import com.itgura.authservice.dto.request.AuthenticationRequest;
import com.itgura.authservice.dto.request.RegisterRequest;
import com.itgura.authservice.dto.request.changeRoleRequest;
import com.itgura.authservice.dto.response.AuthenticationResponse;
import com.itgura.authservice.entity.Role;
import com.itgura.authservice.entity.User;
import com.itgura.authservice.repository.UserRepository;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var  user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        var user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var jwtToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public String changeUserRole( String role) throws ApplicationException {


        String authorizationHeader = UserUtil.extractToken();
        if (!authorizationHeader.isBlank()) {

            String token = authorizationHeader;

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Replace with your actual secret key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            Optional<User> user = userRepository.findByEmail(email);

            if (user.isPresent()) {
                user.get().setRole(Role.valueOf(role));
                userRepository.save(user.get());
                return "Role changed successfully";
            }else {
                throw new ValueNotExistException("User not found");
            }

        }else {
            throw new ApplicationException("Token not found");
        }

    }

    public String changeUserRole(changeRoleRequest request) throws ValueNotExistException {
        String email = request.getUsername();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            user.get().setRole(Role.valueOf(request.getChangeRole()));
            userRepository.save(user.get());
            return "Role changed successfully";
        }else {
            throw new ValueNotExistException("User: "+email+ " not found");
        }
    }
}
