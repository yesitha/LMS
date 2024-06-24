package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.response.hasPermissionResponse;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface ContentPermissionService {
    List<hasPermissionResponse> hasPermissionForContentList(List<UUID> contentIds) throws ApplicationException, URISyntaxException;

}
