package com.itgura.dms_mediator.service;

import com.itgura.dms_mediator.response.dto.DocumentResponseDto;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface DmsService {




    String uploadDocument(MultipartFile file) throws ApplicationException;

    DocumentResponseDto downloadDocument(String alfrescoDocumentId) throws ValueNotFoundException,ApplicationException;

    String deleteDocument(String alfrescoDocumentId) throws ApplicationException,ValueNotFoundException;
}
