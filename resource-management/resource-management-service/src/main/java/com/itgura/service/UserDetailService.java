package com.itgura.service;

import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.request.dto.UserResponseDto;

import javax.security.auth.login.CredentialNotFoundException;

public interface UserDetailService {
    UserResponseDto getLoggedUserDetails(String token) throws BadRequestRuntimeException, CredentialNotFoundException;

}
