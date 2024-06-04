package com.itgura.service.impl;

import com.itgura.dao.UserDao;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    private UserDao userDao;
    @Override
    public UserResponseDto getLoggedUserDetails(String token) throws BadRequestRuntimeException, CredentialNotFoundException {
        String userEmail = UserUtil.getUserEmail(token, secretKey);
        UserResponseDto userDetailByEmail = userDao.getUserDetailByEmail(userEmail);
        return userDetailByEmail;
    }
}
