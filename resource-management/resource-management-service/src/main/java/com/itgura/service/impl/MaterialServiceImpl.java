package com.itgura.service.impl;

import com.itgura.entity.Material;
import com.itgura.entity.MaterialType;
import com.itgura.entity.Session;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.MaterialRepository;
import com.itgura.repository.MaterialTypeRepository;
import com.itgura.repository.SessionRepository;
import com.itgura.request.MaterialRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.response.dto.mapper.MaterialMapper;
import com.itgura.response.dto.mapper.SessionMapper;
import com.itgura.service.MaterialService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MaterialTypeRepository materialTypeRepository;
    @Autowired
    private MaterialRepository materialRepository;


    @Override
    @Transactional
    public String addMaterial(UUID sessionId, MaterialRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                Session session = sessionRepository.findById(sessionId)
                        .orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
                MaterialType materialType = materialTypeRepository.findById(request.getMaterialType())
                        .orElseThrow(() -> new ValueNotExistException("Material Type not found with id " + request.getMaterialType()));
                Material material = new Material();
                material.setMaterialName(request.getMaterialName());
                material.setDescription(request.getDescription());
                material.setIsAvailableForStudents(request.getIsAvailableForStudent());
                material.setReference(request.getReference());
                material.setMaterialType(materialType);
                material.setSession(session);
                material.setCreatedBy(loggedUserDetails.getUserId());
                material.setCreatedOn(new Date(System.currentTimeMillis()));

                materialRepository.save(material);
                return "Material added successfully";
            } else {
                throw new ForbiddenException("You are not allowed to add material");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    @Transactional
    public String updateMaterial(UUID materialId, MaterialRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {

                    Material material = materialRepository.findById(materialId)
                            .orElseThrow(() -> new ValueNotExistException("Material not found with id " + materialId));
                    material.setMaterialName(request.getMaterialName());
                    material.setDescription(request.getDescription());
                    material.setIsAvailableForStudents(request.getIsAvailableForStudent());
                    material.setReference(request.getReference());
                    MaterialType materialType = materialTypeRepository.findById(request.getMaterialType())
                            .orElseThrow(() -> new ValueNotExistException("Material Type not found with id " + request.getMaterialType()));
                    material.setMaterialType(materialType);
                    material.setLastModifiedBy(loggedUserDetails.getUserId());
                    material.setLastModifiedOn(new Date(System.currentTimeMillis()));
                    materialRepository.save(material);
                    return "Material updated successfully";


            } else {
                throw new ForbiddenException("You are not allowed to update material");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String deleteMaterial(UUID materialId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                Material material = materialRepository.findById(materialId)
                        .orElseThrow(() -> new ValueNotExistException("Material not found with id " + materialId));
                materialRepository.delete(material);
                return "Material deleted successfully";
            } else {
                throw new ForbiddenException("You are not allowed to delete material");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

//    @Override
//    public MaterialResponseDto findMaterialById(UUID materialId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
//        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
//        if(loggedUserDetails == null){
//            throw new ValueNotExistException("User not found");
//        }
//        if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
//            Material material = materialRepository.findById(materialId)
//                    .orElseThrow(() -> new ValueNotExistException("Material not found with id " + materialId));
//            MaterialResponseDto dto = MaterialMapper.INSTANCE.toDto(material);
//            return dto;
//
//        }else{
//
//
//        }
//    }
//
//    @Override
//    public List<MaterialResponseDto> findAllMaterial(UUID sessionId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
//        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
//        if(loggedUserDetails == null){
//            throw new ValueNotExistException("User not found");
//        }
//        if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
//
//        }else{
//
//        }
//    }
}
