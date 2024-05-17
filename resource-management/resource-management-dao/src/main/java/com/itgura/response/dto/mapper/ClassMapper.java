package com.itgura.response.dto.mapper;

import com.itgura.entity.AClass;
import com.itgura.response.dto.ClassResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ClassMapper {
    public ClassResponseDto toDto(AClass aClass){
        return ClassResponseDto.builder()
                .id(aClass.getContentId())
                .name(aClass.getClassName())
                .year(aClass.getYear())
                .build();
    }
    public List<ClassResponseDto> toDtoList(List<AClass> aClassList){
        return aClassList.stream().map(this::toDto).collect(Collectors.toList());
    }

}
