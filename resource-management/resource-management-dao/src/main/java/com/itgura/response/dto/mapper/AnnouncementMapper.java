package com.itgura.response.dto.mapper;

import com.itgura.entity.Announcement;
import com.itgura.response.dto.AnnouncementResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    AnnouncementMapper INSTANCE = Mappers.getMapper(AnnouncementMapper.class);

    @Mapping(source = "announcementId", target = "announcementId")
    @Mapping(source = "announcementTitle", target = "announcementTitle")
    @Mapping(source = "announcementDescription", target = "announcementDescription")
    @Mapping(source = "announcementOn", target = "announcementOn")
    @Mapping(source = "announcementBy", target = "announcementBy")
    AnnouncementResponseDto toDto(Announcement announcement);

    List<AnnouncementResponseDto> toDtoList(List<Announcement> announcements);
}
