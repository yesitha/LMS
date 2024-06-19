package com.itgura.paymentservice.service;

import com.itgura.exception.ApplicationException;
import com.itgura.paymentservice.dto.request.hasPermissionRequest;


public interface PermissionService {
    Boolean hasPermission(hasPermissionRequest data) throws ApplicationException;
}
