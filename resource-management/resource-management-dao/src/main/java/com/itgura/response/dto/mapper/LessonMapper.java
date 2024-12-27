package com.itgura.response.dto.mapper;

import com.itgura.entity.Lesson;
import com.itgura.response.dto.LessonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.List;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(source = "contentId", target = "id")
    @Mapping(source = "AClass.contentId", target = "classId")
    @Mapping(source = "isAvailableForStudents", target = "isAvailableForAllStudents")
    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    @Mapping(source = "contentAccessType", target = "contentAccesstype")
    @Mapping(target = "image", expression = "java(convertImageToBase64(lesson.getImage()))")
    LessonResponseDto toDto(Lesson lesson);

    List<LessonResponseDto> toDtoList(List<Lesson> lessons);

    // Utility method for image conversion
    default String convertImageToBase64(Byte[] image) {

            if (image == null) return null;

            // Convert Byte[] to byte[]
            byte[] primitiveBytes = new byte[image.length];
            for (int i = 0; i < image.length; i++) {
                primitiveBytes[i] = image[i];
            }

            return Base64.getEncoder().encodeToString(primitiveBytes);
        }
}