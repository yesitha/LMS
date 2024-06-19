package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.ClassRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.hasPermissionResponse;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface ClassService {
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException;
    List<ClassResponseDto> getAllClasses();

    public String create(ClassRequest request) throws ValueNotExistException;

    Double getClassFee(UUID id) throws ValueNotExistException;

    List<hasPermissionResponse> test() throws ApplicationException, URISyntaxException;
}
