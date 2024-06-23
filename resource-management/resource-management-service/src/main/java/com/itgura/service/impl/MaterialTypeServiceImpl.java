package com.itgura.service.impl;

import com.itgura.entity.MaterialType;
import com.itgura.repository.MaterialTypeRepository;
import com.itgura.response.dto.MaterialTypeResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.response.dto.mapper.MaterialTypeMapper;
import com.itgura.response.dto.mapper.SessionMapper;
import com.itgura.service.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {
    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Override
    public List<MaterialTypeResponseDto> findAllMaterialType() {
        List<MaterialType> all = materialTypeRepository.findAll();
        List<MaterialTypeResponseDto> dtoList = MaterialTypeMapper.INSTANCE.toDtoList(all);
        return dtoList;
    }
}
