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
import com.itgura.request.VideoUploadRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.PreSignedUrlToUploadVideoResponseDto;
import com.itgura.service.MaterialService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.internal.utils.SigningUtils;
import software.amazon.awssdk.services.cloudfront.model.CustomSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.google.common.io.Files.getFileExtension;


@Service
public class MaterialServiceImpl implements MaterialService {
    private final S3Presigner s3Presigner;
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
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public MaterialServiceImpl(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Generate signed url for video material
    @Override
    public String getVideoMaterialSignedUrl(SignedUrlRequest signedUrlRequest) throws Exception {

        CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
        Instant expirationDate = Instant.ofEpochSecond(Instant.now().getEpochSecond() + (signedUrlRequest.getExpiresInHours() * 3600L));


        CustomSignerRequest customSignerRequest = CustomSignerRequest.builder()
                .resourceUrl(cloudFrontDomain + "/" + signedUrlRequest.getFilePath())
                .expirationDate(expirationDate)
                .ipRange(signedUrlRequest.getUserIpAddress())
                .keyPairId(keyPairId)
                .privateKey(new ClassPathResource(privateKeyPath).getFile().toPath())
                .build();


        SignedUrl signedUrl = cloudFrontUtilities.getSignedUrlWithCustomPolicy(customSignerRequest);
        return signedUrl.url();


//        long expirationTime = Instant.now().getEpochSecond() + (signedUrlRequest.getExpiresInHours() * 3600L);
//
//       //  Define policy with IP restriction and expiration time
//        String policy = String.format(
//                "{\"Statement\":[{\"Resource\":\"%s/%s\",\"Condition\":{\"DateLessThan\":{\"AWS:EpochTime\":%d},\"IpAddress\":{\"AWS:SourceIp\":\"%s\"}}}]}",
//                cloudFrontDomain, signedUrlRequest.getFilePath(), expirationTime, signedUrlRequest.getUserIpAddress());
//
////        String policy = String.format(
////                "{\"Statement\":[{\"Resource\":\"%s/%s\"}]}",
////                cloudFrontDomain, signedUrlRequest.getFilePath());
//        System.out.println("policy: "+policy);
//        // Base64 encode the policy
//        String base64EncodedPolicy = Base64.getEncoder().encodeToString(policy.getBytes());
//
//        String decodedPolicy = new String(Base64.getDecoder().decode(base64EncodedPolicy));
//        System.out.println("Decoded Policy: " + decodedPolicy);
//
//        // Sign the policy with the private key
//        String signature = signPolicyWithPrivateKey(base64EncodedPolicy);
//        System.out.println("Signature: "+signature);
//
//
//        // Construct the signed URL
//        return String.format("%s/%s?Policy=%s&Signature=%s&Key-Pair-Id=%s",
//                cloudFrontDomain, signedUrlRequest.getFilePath(),
//                urlEncode(base64EncodedPolicy),
//                urlEncode(signature),
//                keyPairId);

    }

    @Override
    public PreSignedUrlToUploadVideoResponseDto getPreSignedUrlToUploadVideo(VideoUploadRequest videoUploadRequest) throws ValueNotExistException, RuntimeException {



        try {

            Material material = new Material();

            material.setMaterialName(videoUploadRequest.getOriginalFileName());

            String fileExtension = getFileExtension(videoUploadRequest.getOriginalFileName());

            material.setStatus("PENDING");
            material.setSignedUrlExpireTime(videoUploadRequest.getSignedUrlExpireTime());
            material.setSession(sessionRepository.findById(videoUploadRequest.getSessionId())
                    .orElseThrow(() -> new ValueNotExistException("Session not found with id " + videoUploadRequest.getSessionId())));
            material.setMaterialType(materialTypeRepository.findByName("VIDEO"));
            material.setIsAvailableForStudents(videoUploadRequest.getIsAvailableForStudents());
            material.setCreatedOn(new Date(System.currentTimeMillis()));
            material.setCreatedBy(userDetailService.getLoggedUserDetails(UserUtil.extractToken()).getUserId());

            Material materialInstance = materialRepository.save(material);
            UUID uuid = materialInstance.getContentId();
            String s3Key = "videos/" + uuid.toString()+"."+fileExtension;
            materialInstance.setReference(s3Key);
            materialRepository.save(materialInstance);

            // Generate pre-signed URL
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .putObjectRequest(putObjectRequest)
                    .signatureDuration(Duration.ofMinutes(60))
                    .build();

            return new PreSignedUrlToUploadVideoResponseDto(uuid, s3Presigner.presignPutObject(presignRequest).url().toString());

        } catch (ValueNotExistException e) {
            throw new ValueNotExistException("Session not found with id " + videoUploadRequest.getSessionId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String markedVideoAsUploaded(UUID uuid) throws ValueNotExistException {
        Material material = materialRepository.findById(uuid)
                .orElseThrow(() -> new ValueNotExistException("Material not found with id " + uuid));
        material.setStatus("UPLOADED");
        materialRepository.save(material);
        return "Video marked as uploaded";
    }


    private String signPolicyWithPrivateKey(String base64EncodedPolicy) throws Exception {
        // Add the Bouncy Castle provider
        Security.addProvider(new BouncyCastleProvider());

        ClassPathResource resource = new ClassPathResource(privateKeyPath);
        byte[] privateKeyBytes = Files.readAllBytes(resource.getFile().toPath());

//        // Convert to string and strip PEM markers
//        String privateKeyContent = new String(privateKeyBytes);
//        privateKeyContent = privateKeyContent
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s+", ""); // Remove all whitespace
//
//        // Decode Base64 content
//        byte[] decodedKey = Base64.getDecoder().decode(privateKeyContent);


        // Generate the private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
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


    public String generateCustomSignedUrl(String resourcePath, Instant activeDate, Instant expirationDate, String ipAddress) throws Exception {
        // Load private key
        PrivateKey privateKey = loadPrivateKey();

        // Build custom policy
        String resourceUrl = cloudFrontDomain + "/" + resourcePath;
        String customPolicy = SigningUtils.buildCustomPolicyForSignedUrl(resourceUrl, activeDate, expirationDate, ipAddress);
        System.out.println("Custom Policy: " + customPolicy);

        // Sign the policy
        byte[] signatureBytes = SigningUtils.signWithSha1Rsa(customPolicy.getBytes(), privateKey);
        String urlSafeSignature = SigningUtils.makeBytesUrlSafe(signatureBytes);

        // Base64 encode the policy
        String base64EncodedPolicy = Base64.getEncoder().encodeToString(customPolicy.getBytes());

        // Construct the signed URL
        return String.format("%s/%s?Policy=%s&Signature=%s&Key-Pair-Id=%s",
                cloudFrontDomain,
                resourcePath,
                urlSafeEncode(base64EncodedPolicy),
                urlSafeSignature,
                keyPairId);
    }

    private PrivateKey loadPrivateKey() throws Exception {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private String urlSafeEncode(String value) {
        return value.replace("+", "-").replace("=", "_").replace("/", "~");
    }

    @Scheduled(fixedRate = 86400000) // Run daily
    public void cleanupOrphanedMaterials() {
        Instant cutoffTime = Instant.now().minusSeconds(86400);// 24 hours
        List<Material> materials = materialRepository.findAllByStatusAndCreatedAtBefore("PENDING", cutoffTime);
        for (Material material : materials) {

            try {
                materialRepository.delete(material);
                System.out.println("Deleted orphaned materials: " + material.getMaterialName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
