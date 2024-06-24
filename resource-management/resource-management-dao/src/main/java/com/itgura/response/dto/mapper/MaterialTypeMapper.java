package com.itgura.response.dto.mapper;

import com.itgura.entity.Lesson;
import com.itgura.entity.MaterialType;
import com.itgura.response.dto.LessonResponseDto;
import com.itgura.response.dto.MaterialTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MaterialTypeMapper {
    MaterialTypeMapper INSTANCE = Mappers.getMapper(MaterialTypeMapper.class);
    @Mapping(source = "materialTypeId", target = "id")
    MaterialTypeResponseDto toDto(MaterialType materialType);

    List<MaterialTypeResponseDto> toDtoList(List<MaterialType> materialTypes);
}
