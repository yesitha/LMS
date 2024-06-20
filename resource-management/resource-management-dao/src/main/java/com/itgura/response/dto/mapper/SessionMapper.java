package com.itgura.response.dto.mapper;

import com.itgura.entity.Material;
import com.itgura.entity.MaterialType;
import com.itgura.entity.Session;
import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.response.dto.MaterialTypeResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);
    @Mapping(source = "contentId", target = "id")

    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    @Mapping(source = "contentAccessType", target = "contentAccesstype")

    SessionResponseDto toDto(Session session);

    List<SessionResponseDto> toDtoList(List<Session> sessions);
    default List<MaterialResponseDto> materialListToMaterialResponseDtoList(List<Material> materials) {
        return Mappers.getMapper(MaterialMapper.class).toDtoList(materials);
    }
}
