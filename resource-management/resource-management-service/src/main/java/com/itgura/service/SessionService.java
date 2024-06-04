package com.itgura.service;

import com.itgura.request.SessionRequest;

import java.util.UUID;

public interface SessionService {
    String createSession(String token,UUID lessonId, SessionRequest request);
    String updateSession(String token,UUID sessionId, SessionRequest request);
    String deleteSession(String token,UUID sessionId);
    String findSession(String token,UUID sessionId);
    String findAllSession(String token,UUID lessonId);
}
