package com.itgura.response.dto.mapper;

import com.itgura.entity.Material;
import com.itgura.entity.MaterialType;
import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.response.dto.MaterialTypeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MaterialMapper {
    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    @Mapping(source = "contentId", target = "id")
    @Mapping(source = "materialType", target = "materialType")
    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    @Mapping(source = "contentAccessType", target = "contentAccesstype")
    MaterialResponseDto toDto(Material material);

    List<MaterialResponseDto> toDtoList(List<Material> materials);

    default MaterialTypeResponseDto materialTypeToMaterialTypeResponseDto(MaterialType materialType) {
        return Mappers.getMapper(MaterialTypeMapper.class).toDto(materialType);
    }
}
