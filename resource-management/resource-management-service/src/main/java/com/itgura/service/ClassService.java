package com.itgura.service;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.ClassRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.hasPermissionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface ClassService {
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException;
    List<ClassResponseDto> getAllClasses();

    public String create(ClassRequest request, MultipartFile file) throws ValueNotExistException;
    public String update( UUID classId, ClassRequest request,MultipartFile file) throws ValueNotExistException;

    Double getClassFee(UUID id) throws ValueNotExistException;




}
