package com.itgura.authservice.controller;


import com.itgura.authservice.dto.request.AuthenticationRequest;
import com.itgura.authservice.dto.request.RegisterRequest;
import com.itgura.authservice.dto.request.changeRoleRequest;
import com.itgura.authservice.dto.response.AuthenticationResponse;
import com.itgura.authservice.services.AuthenticationService;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth-service")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AppResponse<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        try {
            AuthenticationResponse res = authenticationService.register(registerRequest);
            return AppResponse.ok(res);
        } catch (Exception e) {
            return AppResponse.error(null, "Server Error", "500", "", e.getMessage());
        }

    }

    @PostMapping("/authenticate")
    public AppResponse<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            AuthenticationResponse res = authenticationService.authenticate(authenticationRequest);
            return AppResponse.ok(res);
        } catch (Exception e) {
            return AppResponse.error(null, "Server Error", "500", "", e.getMessage());

        }

    }

    @PostMapping("/refresh")
    public AppResponse<AuthenticationResponse> refresh(@RequestParam("token") String refreshToken){
        try {
            AuthenticationResponse res = authenticationService.refreshToken(refreshToken);
            return AppResponse.ok(res);
        } catch (Exception e) {
            return AppResponse.error(null, "Server Error", "500", "", e.getMessage());
        }


    }

    @GetMapping("/validateToken")
    public AppResponse<Boolean> validateToken(@RequestParam("token") String token){
        try {
            Boolean res = authenticationService.validateToken(token);
            return AppResponse.ok(res);
        } catch (Exception e) {
            return AppResponse.error(null, "Server Error", "500", "", e.getMessage());
        }
    }

    //ToDo: Remove this endpoint after testing

    @PostMapping("/changeUserRoleTemp/{role}")
    public AppResponse<String> changeUserRoleTemp(@PathVariable String role) {
        {
            try {
                return AppResponse.ok(authenticationService.changeUserRole(role));
            } catch (ValueNotFoundException e) {
                return AppResponse.error(null, "Value not found", "404", "", e.getMessage());
            } catch (ApplicationException e) {
                return AppResponse.error(null, "Application Error", "500", "", e.getMessage());
            }


        }
    }


}
