package com.itgura.service.impl;

import com.itgura.request.dto.UserResponseDto;
import com.itgura.service.UserDetailService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailService {
    @Override
    public UserResponseDto getLoggedUserDetails() {
        return null;
    }
}
