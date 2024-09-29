package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.SessionRequest;
import com.itgura.response.dto.SessionResponseDto;

import javax.security.auth.login.CredentialNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface SessionService {
    String createSession(UUID lessonId, SessionRequest request);

    SessionResponseDto findSessionById(UUID sessionId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;
    List<SessionResponseDto> findAllSession( UUID lessonId) throws CredentialNotFoundException, BadRequestRuntimeException, ApplicationException, URISyntaxException;
    List<UUID> findAllSessionsInMonth(UUID classId, int month, int year) throws CredentialNotFoundException, BadRequestRuntimeException, ValueNotExistException;
    String updateSession(UUID sessionId, SessionRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;
    String deleteSession(UUID sessionId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException;

    List<UUID> givePermissionToVideos(UUID sessionId, String email) throws ValueNotExistException;
}
