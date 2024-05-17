package com.itgura.service;

import com.itgura.exception.ValueNotExistException;
import com.itgura.response.dto.ClassResponseDto;

import java.util.List;
import java.util.UUID;

public interface ClassService {
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException;
    List<ClassResponseDto> getAllClasses();
}
