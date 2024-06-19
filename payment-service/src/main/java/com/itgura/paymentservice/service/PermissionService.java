package com.itgura.paymentservice.service;

import com.itgura.exception.ApplicationException;
import com.itgura.paymentservice.dto.request.hasPermissionRequest;
import com.itgura.paymentservice.dto.response.hasPermissionResponse;

import java.util.List;


public interface PermissionService {
    List<hasPermissionResponse> hasPermission(hasPermissionRequest data) throws ApplicationException;
}
