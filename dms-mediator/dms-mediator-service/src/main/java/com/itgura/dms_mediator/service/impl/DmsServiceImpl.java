package com.itgura.dms_mediator.service.impl;
import com.itgura.dms_mediator.response.dto.DocumentResponseDto;
import com.itgura.dms_mediator.service.DmsService;
import com.itgura.dms_mediator.service.config.AlfrescoConfig;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.UUID;
import org.apache.commons.io.IOUtils;


@Service
public class DmsServiceImpl implements DmsService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AlfrescoConfig config;


    @Override
    public String uploadDocument(MultipartFile file) throws ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());

        StringBuilder urlString = new StringBuilder();
        urlString.append(config.getAlfrescoBaseUrl() + config.getUploadUrl());

        try {
//

            // Generate a unique file name by appending _id_[Random UUID]
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String uniqueFileName = baseName + "_id_" + UUID.randomUUID() + extension;

            // Convert multipart file to File with unique file name
            File tempFile = convertMultiPartToFile(file, uniqueFileName);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setContentLength(tempFile.length());

//
            MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();

            fileValueMap.add("filedata", new FileSystemResource(tempFile));
            fileValueMap.add("relativePath", config.getUploadRelativePath());
            fileValueMap.add("name", uniqueFileName);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(fileValueMap,
                    headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(urlString.toString(), HttpMethod.POST, entity,
                    String.class);


            tempFile.delete();


            JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
            String alfrescoDocumentId = jsonResponse.getJSONObject("entry").getString("id");

            if (alfrescoDocumentId != null) {
                return alfrescoDocumentId;
            } else {
                throw new ApplicationException("Failed to upload document to Alfresco: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception: " + e.getMessage(), e);
        }

    }

    @Override
    public DocumentResponseDto downloadDocument(String alfrescoDocumentId) throws ValueNotFoundException, ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername().trim(), config.getPassword());

        // URL to access the document content
        String fileUrl = config.getAlfrescoBaseUrl() + config.getDownloadUrl();
        StringBuilder urlString = new StringBuilder();
        urlString.append(MessageFormat.format(fileUrl, alfrescoDocumentId));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<Resource> response;
        try {
            response = restTemplate.exchange(
                    urlString.toString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Resource.class);
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ValueNotFoundException("Document not found: " + e.getStatusCode());
            } else {
                throw new ApplicationException("Client error during document download: " + e.getStatusCode());
            }
        } catch (HttpServerErrorException e) {

            throw new ApplicationException("Server error during document download: " + e.getStatusCode());
        }

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

            Resource imageResource = response.getBody();
            String base64Image = convertResourceToBase64(imageResource);
            // Extract filename from 'Content-Disposition' header if available
            String filename = extractOriginalFileName(response.getHeaders().getContentDisposition().getFilename());

            return new DocumentResponseDto(base64Image, filename);
        } else {
            // Handle unexpected scenarios
            throw new ApplicationException("Unexpected error during document download: " + response.getStatusCode());
        }
    }

    @Override
    public String deleteDocument(String alfrescoDocumentId) throws ApplicationException, ValueNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());

        // Include the 'permanent=true' query parameter in the delete URL
        String deleteUrl = config.getAlfrescoBaseUrl() + config.getDeleteUrl();
        StringBuilder urlString = new StringBuilder();
        urlString.append(MessageFormat.format(deleteUrl, alfrescoDocumentId));

        try {
            restTemplate.exchange(urlString.toString(), HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
            return "Document with ID: " + alfrescoDocumentId + " deleted successfully.";
        } catch (HttpClientErrorException.NotFound e) {
            throw new ValueNotFoundException("Document not found with ID: " + alfrescoDocumentId);
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error during document deletion: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new ApplicationException("Server error during document deletion: " + e.getMessage());
        } catch (RestClientException e) {
            throw new ApplicationException("Error during document deletion: " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Unexpected error during document deletion: " + e.getMessage());
        }
    }


    public String extractOriginalFileName(String uniqueFileName) {

        if (uniqueFileName.contains("_id_")) {
            String baseName = uniqueFileName.split("_id_")[0];
            String extension = uniqueFileName.substring(uniqueFileName.lastIndexOf("."));
            return baseName + extension;
        }
        throw new RuntimeException("Invalid uniqueFileName: " + uniqueFileName);
    }

    private String convertResourceToBase64(Resource resource) throws ApplicationException {
        try {
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new ApplicationException("Image convert error: " + e.getMessage());
        }
    }


    private File convertMultiPartToFile(MultipartFile file, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        file.transferTo(convFile);
        return convFile;
    }

}


