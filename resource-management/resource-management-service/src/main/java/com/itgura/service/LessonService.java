package com.itgura.service;

import com.itgura.exception.ValueNotExistException;
import com.itgura.request.LessonRequest;
import com.itgura.response.dto.LessonResponseDto;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    String saveLesson(LessonRequest request) throws ValueNotExistException;
    String updateLesson(LessonRequest request, UUID id) throws ValueNotExistException;
    String deleteLesson(UUID id) throws ValueNotExistException;

    LessonResponseDto findLesson(UUID id) throws ValueNotExistException;
    List<LessonResponseDto> findAllLesson();
}
