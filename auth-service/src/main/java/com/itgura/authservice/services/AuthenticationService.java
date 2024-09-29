package com.itgura.authservice.services;

import com.itgura.authservice.dto.request.AuthenticationRequest;
import com.itgura.authservice.dto.request.RegisterRequest;
import com.itgura.authservice.dto.response.AuthenticationResponse;
import com.itgura.authservice.entity.Role;
import com.itgura.authservice.entity.User;
import com.itgura.authservice.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;

//    public AuthenticationResponse register(RegisterRequest registerRequest) {
//        var  user = User.builder()
//                .firstName(registerRequest.getFirstName())
//                .lastName(registerRequest.getLastName())
//                .email(registerRequest.getEmail())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .role(Role.STUDENT)
//                .build();
//        userRepository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefresh(new HashMap<>(),user);
//        return AuthenticationResponse.builder()
//                .authenticationToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
//        );
//        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefresh(new HashMap<>(),user);
//        return AuthenticationResponse.builder()
//                .authenticationToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }

    // This method is invoked after a successful Google OAuth2 login
    public AuthenticationResponse processOAuthPostLogin(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        // Check if the user already exists in your database
        var user = userRepository.findByEmail(email).orElseGet(() -> {
            // Register the user if they don't exist
            User newUser = User.builder()
                    .firstName(oAuth2User.getAttribute("given_name")) // Extract first name from OAuth2 user info
                    .lastName(oAuth2User.getAttribute("family_name"))  // Extract last name
                    .email(email)
                    .role(Role.STUDENT) // Assign default role (can be dynamic)
                    .build();
            return userRepository.save(newUser);
        });

        // Generate JWT and refresh token
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);

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

//    public AuthenticationResponse getAccessToken(OAuth2AuthenticationToken auth) {
//        OAuth2User oAuth2User = auth.getPrincipal();
//        String email = oAuth2User.getAttribute("email");
//
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
//
//        // Generate JWT and refresh token
//        String jwtToken = jwtService.generateToken(user);
//        String refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
//
//        return AuthenticationResponse.builder()
//                .authenticationToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
}
