package com.itgura.service;

import com.itgura.request.SessionRequest;

import java.util.UUID;

public interface SessionService {
    String createSession(UUID lessonId, SessionRequest request);
    String updateSession(UUID sessionId, SessionRequest request);
    String deleteSession(UUID sessionId);
    String findSession(UUID sessionId);
    String findAllSession(UUID lessonId);
}
