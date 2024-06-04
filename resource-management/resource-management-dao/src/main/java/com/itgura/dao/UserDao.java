package com.itgura.dao;

import com.itgura.request.dto.UserResponseDto;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.CredentialNotFoundException;


public interface UserDao {
    public UserResponseDto getUserDetailByEmail(String email) throws CredentialNotFoundException;
}
