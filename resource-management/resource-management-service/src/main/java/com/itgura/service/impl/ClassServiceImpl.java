package com.itgura.service.impl;

import com.itgura.entity.AClass;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Override
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException {
        AClass aClass = classRepository.findById(id)
                .orElseThrow(() -> new ValueNotExistException("Class not found with id " + id));
        return ClassMapper.INSTANCE.toDto(aClass);
    }
    @Override
    public List<ClassResponseDto> getAllClasses() {
        List<AClass> classList = classRepository.findAll();
        return ClassMapper.INSTANCE.toDtoList(classList);
    }
}
