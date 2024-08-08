package com.itgura.response.dto.mapper;

import com.itgura.entity.ForumImage;
import com.itgura.response.dto.ForumImageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ForumImageMapper {
    ForumImageMapper INSTANCE = Mappers.getMapper(ForumImageMapper.class);

    ForumImageResponseDto toDto(ForumImage forumImage);

    List<ForumImageResponseDto> toDtoList(List<ForumImage> forumImages);
}
