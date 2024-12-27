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
import com.itgura.request.SignedUrlRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.*;





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

    @Value("${cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${cloudfront.keyPairId}")
    private String keyPairId;

    @Value("${cloudfront.privateKeyPath}")
    private String privateKeyPath;



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
    // Generate signed url for video material
    @Override
    public String getVideoMaterialSignedUrl(SignedUrlRequest signedUrlRequest) throws Exception {

        long expirationTime = Instant.now().getEpochSecond() + (signedUrlRequest.getExpiresInHours() * 3600L);

        // Define policy with IP restriction and expiration time
        String policy = String.format(
                "{\"Statement\":[{\"Resource\":\"%s/%s\",\"Condition\":{\"DateLessThan\":{\"AWS:EpochTime\":%d},\"IpAddress\":{\"AWS:SourceIp\":\"%s\"}}}]}",
                cloudFrontDomain, signedUrlRequest.getFilePath(), expirationTime, signedUrlRequest.getUserIpAddress());
        // Base64 encode the policy
        String base64EncodedPolicy = Base64.getEncoder().encodeToString(policy.getBytes());

        // Sign the policy with the private key
        String signature = signPolicyWithPrivateKey(base64EncodedPolicy);


        // Construct the signed URL
        return String.format("%s/%s?Policy=%s&Signature=%s&Key-Pair-Id=%s",
                cloudFrontDomain, signedUrlRequest.getFilePath(),
                urlEncode(base64EncodedPolicy),
                urlEncode(signature),
                keyPairId);

    }

    private String signPolicyWithPrivateKey(String base64EncodedPolicy) throws Exception {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = java.security.KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(base64EncodedPolicy.getBytes());
        byte[] signedBytes = signature.sign();

        return Base64.getEncoder().encodeToString(signedBytes);
    }


    private String urlEncode(String value) {
        return value.replace("+", "-").replace("=", "_").replace("/", "~");
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
