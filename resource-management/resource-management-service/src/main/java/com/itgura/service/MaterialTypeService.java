package com.itgura.service;

import com.itgura.response.dto.MaterialTypeResponseDto;

import java.util.List;

public interface MaterialTypeService {
    List<MaterialTypeResponseDto> findAllMaterialType();
}
