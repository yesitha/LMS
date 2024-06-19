package com.itgura.service.impl;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.entity.AClass;
import com.itgura.entity.Fees;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.repository.FeesRepository;
import com.itgura.request.ClassRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.request.hasPermissionRequest;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.response.hasPermissionResponse;
import com.itgura.service.ClassService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private FeesRepository feesRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException {
        AClass aClass = classRepository.findById(id)
                .orElseThrow(() -> new ValueNotExistException("Class not found with id " + id));
        return ClassMapper.INSTANCE.toDto(aClass);
    }
    @Override
    public List<ClassResponseDto> getAllClasses() {
        List<AClass> classList = classRepository.findAll();
        return ClassMapper.INSTANCE.toDtoList(classList);
    }

    @Override
    @Transactional
    public String create(ClassRequest request) throws ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }
            if(!loggedUserDetails.getUserRoles().equals("ADMIN")){
                throw new ForbiddenException("User is not authorized to perform this operation");
            }
            UUID userId = loggedUserDetails.getUserId();
            Fees fees = new Fees();
            fees.setAmount(request.getFees());
            Fees savedFees = feesRepository.save(fees);
            AClass aClass = new AClass();
            aClass.setClassName(request.getClassName());
            aClass.setClassName(request.getClassName());
            aClass.setFees(savedFees);
            aClass.setCreatedBy(userId);
            aClass.setCreatedOn(new Date(System.currentTimeMillis()));
            aClass.setLastModifiedOn(new Date(System.currentTimeMillis()));
            aClass.setLastModifiedBy(userId);
            classRepository.save(aClass);
            return "Class saved successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getClassFee(UUID id) throws ValueNotExistException {

        AClass aClass = classRepository.findById(id)
                .orElseThrow(() -> new ValueNotExistException("Class not found with id " + id));
        return aClass.getFees().getAmount();

    }

    @Override
    public List<hasPermissionResponse> test() throws ApplicationException, URISyntaxException {
        URI uri = new URI("http://lms-gateway/payment-service/hasPermission") ;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
        // Create request body


        AppRequest<hasPermissionRequest> requestBody = new AppRequest<>();
        hasPermissionRequest data = new hasPermissionRequest();
        data.setContentIds(Collections.singletonList(UUID.randomUUID()));
        requestBody.setData(data);
        // Set the fields of the requestBody object as needed

        // Create HttpEntity with body and headers
        HttpEntity<AppRequest<hasPermissionRequest>> entity = new HttpEntity<>(requestBody, headers);




        try {
            ResponseEntity<AppResponse> responseEntity = restTemplate.postForEntity(uri, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting monthly payment: response or data is null");
            }

            return (List<hasPermissionResponse>) response.getData();


        } catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }
    }

}
