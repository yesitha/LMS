package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.UserDetailRequest;
import com.itgura.response.dto.UserDetailsResponse;
import com.itgura.service.UserDetailService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class UserController {
    @Autowired
    private UserDetailService userService;
    @GetMapping(ResourceManagementURI.USER_DETAILS)
    public AppResponse<UserDetailsResponse> getUserDetails(){
        try{
            UserDetailsResponse response = userService.findUser();
            return AppResponse.ok(response);
        }
            
        catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PostMapping(ResourceManagementURI.USER_DETAILS)
    public AppResponse<UserDetailsResponse> registerUser(@RequestBody @Valid UserDetailRequest userDetailRequest) {
        try {
            UserDetailsResponse response = userService.registerUser(userDetailRequest);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
    @PutMapping(ResourceManagementURI.USER_DETAILS)
    public AppResponse<UserDetailsResponse> updateUser(@RequestBody @Valid UserDetailRequest userDetailRequest) {
        try {
            UserDetailsResponse response = userService.updateUser(userDetailRequest);
            return AppResponse.ok(response);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }
}
