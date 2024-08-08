package com.itgura.response.dto.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itgura.entity.ForumImage;
import com.itgura.response.dto.ForumImageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ForumImageMapper {
    ForumImageMapper INSTANCE = Mappers.getMapper(ForumImageMapper.class);

    ForumImageResponseDto toDto(ForumImage forumImage);

    List<ForumImageResponseDto> toDtoList(List<ForumImage> forumImages);
}
