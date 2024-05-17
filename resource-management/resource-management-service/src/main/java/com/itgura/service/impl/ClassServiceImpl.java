package com.itgura.service.impl;

import com.itgura.entity.AClass;
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

    private final ClassMapper classMapper =  new ClassMapper();
    @Override
    public List<ClassResponseDto> getAllClasses() {
        try {
            List<AClass> all = classRepository.findAll();
            return classMapper.toDtoList(all);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
