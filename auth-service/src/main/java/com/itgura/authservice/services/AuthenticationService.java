package com.itgura.authservice.services;

import com.itgura.authservice.dto.request.AuthenticationRequest;
import com.itgura.authservice.dto.request.RegisterRequest;
import com.itgura.authservice.dto.request.changeRoleRequest;
import com.itgura.authservice.dto.response.AuthenticationResponse;
import com.itgura.authservice.entity.Role;
import com.itgura.authservice.entity.User;
import com.itgura.authservice.repository.UserRepository;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    @Autowired
    private RestTemplate restTemplate;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = ApplicationException.class)
    public AuthenticationResponse register(RegisterRequest registerRequest) throws IllegalArgumentException, ApplicationException {
        if(isExistingUser(registerRequest.getEmail())){
            throw new IllegalArgumentException("The email is already in use in another account, try logging instead");
        }
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
        try {
            createAccountInResourceService(user.getEmail(), user.getFirstName(), user.getLastName(), user.getId(),jwtToken);
        } catch (Exception e) {

            throw new ApplicationException("Failed to create account in resource service: " + e.getMessage());
        }
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void createAccountInResourceService(String email, String firstName, String lastName, UUID id, String jwtToken) throws  ApplicationException {
        String url = "http://lms-gateway/resource-management/user-details";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);


        // Prepare the request body with only the necessary fields
        // Create a map for the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("registration_number", 0);
        requestBody.put("first_name", firstName);
        requestBody.put("last_name", lastName);
        requestBody.put("user_id", id);
        requestBody.put("email", email);
        requestBody.put("mobile_number", null);
        requestBody.put("examination_year", 0);
        requestBody.put("gender", null);
        requestBody.put("school", null);
        requestBody.put("stream_id", null);
        requestBody.put("address", null);

        // Create the HttpEntity with headers and body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<AppResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();
            System.out.println(response);

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while creating account in resource account");
            }

            System.out.println("Account Created Successfully");



        } catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }


    }

    private boolean isExistingUser(String email) {
        return  userRepository.existsByEmail(email);

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
