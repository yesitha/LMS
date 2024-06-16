package com.itgura.service.impl;

import com.itgura.entity.AClass;
import com.itgura.entity.Fees;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.repository.FeesRepository;
import com.itgura.request.ClassRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.ClassResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.service.ClassService;
import com.itgura.service.UserDetailService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String create(String token,ClassRequest request) throws ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(token);
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
}
