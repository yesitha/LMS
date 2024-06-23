package com.itgura.service;


import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.request.MaterialRequest;
import com.itgura.response.dto.MaterialResponseDto;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.UUID;

public interface MaterialService {
    String addMaterial(UUID sessionId, MaterialRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;
    String updateMaterial(UUID materialId, MaterialRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;
    String deleteMaterial(UUID materialId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;
//    MaterialResponseDto findMaterialById(UUID materialId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;
//    List<MaterialResponseDto> findAllMaterial(UUID sessionId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException;
}
