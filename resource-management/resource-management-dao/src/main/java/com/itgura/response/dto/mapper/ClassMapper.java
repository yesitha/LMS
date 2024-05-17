package com.itgura.response.dto.mapper;

import com.itgura.entity.AClass;
import com.itgura.response.dto.ClassResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    @Mapping(source = "contentId", target = "id")
    ClassResponseDto toDto(AClass aClass);

    List<ClassResponseDto> toDtoList(List<AClass> aClassList);
}
