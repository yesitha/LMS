package com.itgura.controller;


import com.itgura.dms_mediator.response.dto.DocumentResponseDto;
import com.itgura.dms_mediator.service.DmsService;
import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationGeneralException;
import com.itgura.exception.ValueNotFoundException;
import com.itgura.util.DMSMediatorURI;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.DMS_MEDIATOR)
public class DmsController {

    @Autowired
    private DmsService dmsService;


    @PostMapping(value = URIPrefix.DOCUMENTS + DMSMediatorURI.DOCUMENT_UPLOAD, consumes = "multipart/form-data")
    public AppResponse<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            String ref = dmsService.uploadDocument(file);
            return AppResponse.ok(ref);

        } catch (Exception e) {
            return AppResponse.error(null, "error", "Server Error", "500", e.getMessage());
        }

    }


    @GetMapping(value = URIPrefix.DOCUMENTS + DMSMediatorURI.DOWNLOAD_DOCUMENT)

    public AppResponse<DocumentResponseDto> downloadDocument(@RequestParam("fileRefId") String alfrescoDocumentId) {

        try {
            DocumentResponseDto documentResponseDto = this.dmsService.downloadDocument(alfrescoDocumentId);
            return AppResponse.ok(documentResponseDto);

        } catch (ValueNotFoundException e) {
            return AppResponse.error(null, e.getMessage(), "Document not found", "404", "Requested Document not found in alfresco server ");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }

    }

    @DeleteMapping(value = URIPrefix.DOCUMENTS + DMSMediatorURI.DELETE_DOCUMENT)

    public AppResponse<String> deleteDocument(@RequestParam("fileRefId") String alfrescoDocumentId) {

        try {

            return AppResponse.ok(this.dmsService.deleteDocument(alfrescoDocumentId));

        } catch (ValueNotFoundException e) {
            return AppResponse.error(null, e.getMessage(), "Document not found", "404", "Requested Document not found in alfresco server ");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }

    }
}

