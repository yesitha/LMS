package com.itgura.response.dto.mapper;

import com.itgura.entity.Lesson;
import com.itgura.response.dto.LessonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(source = "contentId", target = "id")
    @Mapping(source = "AClass.contentId", target = "classId")
    @Mapping(source = "isAvailableForStudents", target = "isAvailableForAllStudents")
    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    LessonResponseDto toDto(Lesson lesson);

    List<LessonResponseDto> toDtoList(List<Lesson> lessons);
}