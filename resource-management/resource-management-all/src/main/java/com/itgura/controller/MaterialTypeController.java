package com.itgura.controller;

import com.itgura.dto.AppResponse;
import com.itgura.response.dto.MaterialTypeResponseDto;
import com.itgura.service.MaterialTypeService;
import com.itgura.util.ResourceManagementURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.RESOURCE_MANAGEMENT)
public class MaterialTypeController {
    @Autowired
    private MaterialTypeService materialTypeService;

    @GetMapping(ResourceManagementURI.MATERIAL_TYPE+URIPrefix.GET_ALL)
    public AppResponse<List<MaterialTypeResponseDto>> findAllMaterialType() {
        try {
            List<MaterialTypeResponseDto> s = materialTypeService.findAllMaterialType();
            return AppResponse.ok(s);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }
    }


}
