package com.itgura.dms_mediator.service.impl;
import com.itgura.dms_mediator.response.dto.DocumentResponseDto;
import com.itgura.dms_mediator.service.DmsService;
import com.itgura.dms_mediator.service.config.NextCloudConfig;
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
import java.net.URI;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.UUID;
import org.apache.commons.io.IOUtils;


@Service
public class DmsServiceImpl implements DmsService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NextCloudConfig config;


    @Override
    public String uploadDocument(MultipartFile file) throws ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());

        try {

            // Generate a unique file name by appending _id_[Random UUID]
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String uniqueFileName = baseName + "_id_" + UUID.randomUUID() + extension;

            // Build the upload URL
            String path = config.getUploadRelativePath();
            String uploadUrl = config.buildUploadUrl(path + "/" + uniqueFileName);
            System.out.println("uploadUrl: " + uploadUrl);

            // Convert MultipartFile to a File
            File tempFile = convertMultiPartToFile(file, uniqueFileName);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setContentLength(tempFile.length());

            // Create a resource for the file to be uploaded
            FileSystemResource resource = new FileSystemResource(tempFile);

            // Use RestTemplate to upload the file
            HttpEntity<FileSystemResource> entity = new HttpEntity<>(resource, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(uploadUrl, HttpMethod.PUT, entity, String.class);

            // Clean up temporary file
            tempFile.delete();

            // Check for successful response
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return uniqueFileName;  // Return the unique file name or path as a reference ID
            } else {
                throw new ApplicationException("Failed to upload document to Nextcloud: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            throw new ApplicationException("Exception: " + e.getMessage(), e);
        }
    }
    @Override
    public DocumentResponseDto downloadDocument(String documentPath) throws ValueNotFoundException, ApplicationException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername().trim(), config.getPassword());

        // Build the URL to access the document content
        String downloadUrl = config.buildDownloadUrl(config.getUploadRelativePath(), documentPath);
        System.out.println("downloadUrl: " + downloadUrl);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<Resource> response;
        try {
            response = restTemplate.exchange(
                    downloadUrl,
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
            Resource resource = response.getBody();
            String base64Content = convertResourceToBase64(resource);

            // Extract filename from 'Content-Disposition' header if available, or use documentPath
            String filename = extractOriginalFileName(response.getHeaders().getContentDisposition().getFilename());


            return new DocumentResponseDto(base64Content, filename);
        } else {
            // Handle unexpected scenarios
            System.out.println(response);
            throw new ApplicationException("Unexpected error during document download: " + response.getStatusCode());
        }
    }

    @Override
    public String deleteDocument(String documentPath) throws ApplicationException, ValueNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getUsername(), config.getPassword());

        // Build the delete URL using the document path
        String deleteUrl = config.buildDeleteUrl(config.getUploadRelativePath(), documentPath);

        try {
            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
            return "Document at path: " + documentPath + " deleted successfully.";
        } catch (HttpClientErrorException.NotFound e) {
            throw new ValueNotFoundException("Document not found at path: " + documentPath);
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


