package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.AnnouncementRequest;
import com.itgura.response.dto.AnnouncementResponseDto;

import javax.security.auth.login.CredentialNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface AnnouncementService {
    String createAnnouncement(UUID classId, AnnouncementRequest request) throws ApplicationException, URISyntaxException, CredentialNotFoundException, BadRequestRuntimeException;
    AnnouncementResponseDto findAnnouncementById(UUID announcementId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;
    List<AnnouncementResponseDto> findAllAnnouncement(UUID classId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;
    String updateAnnouncement(UUID announcementId, AnnouncementRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;
    String deleteAnnouncement(UUID announcementId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;

}
