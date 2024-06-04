package com.itgura.service;

import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.LessonRequest;
import com.itgura.response.dto.LessonResponseDto;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.UUID;

public interface LessonService {
    String saveLesson(String token,LessonRequest request) throws ValueNotExistException;
    String updateLesson(String token,LessonRequest request, UUID id) throws ValueNotExistException;
    String deleteLesson(String token,UUID id) throws ValueNotExistException;

    LessonResponseDto findLesson(String tooken,UUID id) throws ValueNotExistException;
    List<LessonResponseDto> findAllLesson(String token,UUID classId) throws CredentialNotFoundException, BadRequestRuntimeException,ValueNotExistException;
}
