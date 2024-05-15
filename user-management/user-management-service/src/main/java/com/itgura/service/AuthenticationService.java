package com.itgura.service;

import com.itgura.dto.request.AuthenticationRequest;
import com.itgura.dto.request.RegisterRequest;
import com.itgura.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    public  AuthenticationResponse register(RegisterRequest registerRequest);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public  AuthenticationResponse refreshToken(String refreshToken);
}
