package com.itgura.service;

import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.SessionRequest;
import com.itgura.response.dto.SessionResponseDto;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.UUID;

public interface SessionService {
    String createSession(String token,UUID lessonId, SessionRequest request);

    SessionResponseDto findSessionById(String token,UUID sessionId) throws ValueNotExistException, CredentialNotFoundException, BadRequestRuntimeException;
    List<SessionResponseDto> findAllSession(String token, UUID lessonId) throws CredentialNotFoundException, BadRequestRuntimeException, ValueNotExistException;
}
