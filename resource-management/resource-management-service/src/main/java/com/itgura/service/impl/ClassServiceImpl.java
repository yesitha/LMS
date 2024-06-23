package com.itgura.service.impl;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.entity.AClass;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
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
    private UserDetailService userDetailService;



    @Override
    @Transactional
    public ClassResponseDto getClassById(UUID id) throws ValueNotExistException {
        AClass aClass = classRepository.findById(id)
                .orElseThrow(() -> new ValueNotExistException("Class not found with id " + id));
        return ClassMapper.INSTANCE.toDto(aClass);
    }
    @Override
    @Transactional
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

            AClass aClass = new AClass();
            aClass.setClassName(request.getClassName());
            aClass.setYear(request.getYear());
            aClass.setFees(request.getFees());
            aClass.setCreatedBy(userId);
            aClass.setCreatedOn(new Date(System.currentTimeMillis()));
            aClass.setLastModifiedOn(new Date(System.currentTimeMillis()));
            aClass.setLastModifiedBy(userId);
            aClass.setContentAccessType(request.getContentAccesstype());
            classRepository.save(aClass);
            return "Class saved successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String update(UUID classId, ClassRequest request) throws ValueNotExistException {
        try{
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }
            if(!(loggedUserDetails.getUserRoles().equals("ADMIN")|| loggedUserDetails.getUserRoles().equals("TEACHER"))){
                throw new ForbiddenException("User is not authorized to perform this operation");
            }else{
                UUID userId = loggedUserDetails.getUserId();
                AClass aClass = classRepository.findById(classId)
                        .orElseThrow(() -> new ValueNotExistException("Class not found with id " + classId));
                aClass.setClassName(request.getClassName());
                aClass.setYear(request.getYear());
                aClass.setFees(request.getFees());

                aClass.setContentAccessType(request.getContentAccesstype());
                aClass.setLastModifiedOn(new Date(System.currentTimeMillis()));
                aClass.setLastModifiedBy(userId);
                classRepository.save(aClass);
                return "Class updated successfully";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Double getClassFee(UUID id) throws ValueNotExistException {

        AClass aClass = classRepository.findById(id)
                .orElseThrow(() -> new ValueNotExistException("Class not found with id " + id));
        return aClass.getFees();

    }


}
