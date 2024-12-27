package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.LessonRequest;
import com.itgura.response.dto.LessonResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.UUID;

public interface LessonService {
    String saveLesson(LessonRequest request, MultipartFile file) throws ValueNotExistException;
    String updateLesson(LessonRequest request, UUID id,MultipartFile file) throws ValueNotExistException;
    String deleteLesson(UUID id) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;

    LessonResponseDto findLesson(UUID id) throws ValueNotExistException;
    List<LessonResponseDto> findAllLesson(UUID classId) throws CredentialNotFoundException, BadRequestRuntimeException,ValueNotExistException;
}
