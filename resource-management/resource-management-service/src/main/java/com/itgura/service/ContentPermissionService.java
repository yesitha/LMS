package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.response.hasPermissionResponse;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface ContentPermissionService {
    List<hasPermissionResponse> hasPermissionForContentList(List<UUID> contentIds) throws ApplicationException, URISyntaxException;
    String givePermissionForStudents(UUID classId,int year, int month,UUID contentId) throws ApplicationException, URISyntaxException;
    String deleteSession(UUID contentId) throws ApplicationException, URISyntaxException;

}
