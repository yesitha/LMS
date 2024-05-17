package com.itgura.paymentservice.service;

import com.itgura.paymentservice.dto.request.hasPermissionRequest;


public interface PermissionService {
    Boolean hasPermission(hasPermissionRequest data, String authorizationHeader);
}
