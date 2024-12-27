package com.itgura.response.dto.mapper;

import com.itgura.entity.AClass;
import com.itgura.response.dto.ClassResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    @Mapping(source = "contentId", target = "id")
    @Mapping(source = "contentAccessType", target = "contentAccesstype")
    @Mapping(target = "image", expression = "java(convertImageToBase64(aClass.getImage()))")

    ClassResponseDto toDto(AClass aClass);

    List<ClassResponseDto> toDtoList(List<AClass> aClassList);
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
