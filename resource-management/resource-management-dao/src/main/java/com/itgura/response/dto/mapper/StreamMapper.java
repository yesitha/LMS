package com.itgura.response.dto.mapper;

import com.itgura.entity.MaterialType;
import com.itgura.entity.Stream;
import com.itgura.response.dto.MaterialTypeResponseDto;
import com.itgura.response.dto.StreamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StreamMapper {
    StreamMapper INSTANCE = Mappers.getMapper(StreamMapper.class);
    StreamResponseDto toDto(Stream stream);

    List<StreamResponseDto> toDtoList(List<Stream> materialTypes);
}