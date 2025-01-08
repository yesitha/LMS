package com.itgura.service;

import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.request.UserDetailRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.UserDetailsResponse;

import javax.security.auth.login.CredentialNotFoundException;

public interface UserDetailService {
    UserResponseDto getLoggedUserDetails(String token) throws BadRequestRuntimeException, CredentialNotFoundException;
    UserDetailsResponse findUser();
    UserDetailsResponse registerUser(UserDetailRequest userDetailRequest);
    UserDetailsResponse updateUser(UserDetailRequest userDetailRequest);
}