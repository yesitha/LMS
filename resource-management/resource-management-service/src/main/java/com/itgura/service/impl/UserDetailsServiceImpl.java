package com.itgura.service.impl;

import com.itgura.dao.UserDao;
import com.itgura.entity.Address;
import com.itgura.entity.Stream;
import com.itgura.entity.Student;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotFoundException;
import com.itgura.repository.AddressRepository;
import com.itgura.repository.StreamRepository;
import com.itgura.repository.StudentRepository;
import com.itgura.request.UserDetailRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.UserDetailsResponse;
import com.itgura.response.dto.mapper.SessionMapper;
import com.itgura.response.dto.mapper.UserDetailMapper;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailService {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public UserResponseDto getLoggedUserDetails(String token) throws BadRequestRuntimeException, CredentialNotFoundException {
        String userEmail = UserUtil.getUserEmail(token, secretKey);
        UserResponseDto userDetailByEmail = userDao.getUserDetailByEmail(userEmail);
        return userDetailByEmail;
    }

    @Override
    public UserDetailsResponse findUser() {
        try{
            UserResponseDto loggedUserDetails = getLoggedUserDetails(UserUtil.extractToken());
            UUID userId = loggedUserDetails.getUserId();
            Student student = studentRepository.findByUserId(userId)
                    .orElseThrow(() -> {
                        return new ValueNotFoundException("user not found id: " + userId);
                    });
            UserDetailsResponse dto = UserDetailMapper.INSTANCE.toDto(student);
            dto.setUserRoles(loggedUserDetails.getUserRoles());
            return dto;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public UserDetailsResponse registerUser(UserDetailRequest userDetailRequest) {
        try{
            UserResponseDto loggedUserDetails = getLoggedUserDetails(UserUtil.extractToken());
            UUID userId = loggedUserDetails.getUserId();
            Address address = new Address();
            address.setHouseNameOrNumber(userDetailRequest.getAddress().getHouseNameOrNumber());
            address.setLine1(userDetailRequest.getAddress().getLine1());
            address.setLine2(userDetailRequest.getAddress().getLine2());
            address.setCity(userDetailRequest.getAddress().getCity());
            Address savedAddress = addressRepository.save(address);
            Stream stream = streamRepository.findById(userDetailRequest.getStream())
                    .orElseThrow(() -> {
                        return new ValueNotFoundException("stream not found id: " + userDetailRequest.getStream());
                    });
            Student student = new Student();
            student.setRegistration_number(userDetailRequest.getRegistrationNumber());
            student.setFirstName(userDetailRequest.getFirstName());
            student.setLastName(userDetailRequest.getLastName());
            student.setEmail(userDetailRequest.getEmail());
            student.setMobileNumber(userDetailRequest.getMobileNumber());
            student.setExaminYear(userDetailRequest.getExaminationYear());
            student.setGender(userDetailRequest.getGender());
            student.setSchool(userDetailRequest.getSchool());
            student.setAddress(savedAddress);
            student.setStream(stream);
            student.setUserId(userId);
            Student save = studentRepository.save(student);
            UserDetailsResponse dto = UserDetailMapper.INSTANCE.toDto(save);
            dto.setUserRoles(loggedUserDetails.getUserRoles());
            return dto;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public UserDetailsResponse updateUser(UserDetailRequest userDetailRequest) {
        try {
            // Get logged-in user's details
            UserResponseDto loggedUserDetails = getLoggedUserDetails(UserUtil.extractToken());
            UUID userId = loggedUserDetails.getUserId();
            // Find the existing Student record associated with the logged-in user
            Student student = studentRepository.findByUserId(userId)
                    .orElseThrow(() -> new ValueNotFoundException("User not found with ID: " + userId));
            // Update address if provided
            Address address = student.getAddress();
            if (userDetailRequest.getAddress() != null) {
                if (address == null) {
                    address = new Address();
                }
                address.setHouseNameOrNumber(userDetailRequest.getAddress().getHouseNameOrNumber());
                address.setLine1(userDetailRequest.getAddress().getLine1());
                address.setLine2(userDetailRequest.getAddress().getLine2());
                address.setCity(userDetailRequest.getAddress().getCity());
                addressRepository.save(address); // Save updated address
            }
            // Update stream if provided
            if (userDetailRequest.getStream() != null) {
                Stream stream = streamRepository.findById(userDetailRequest.getStream())
                        .orElseThrow(() -> new ValueNotFoundException("Stream not found with ID: " + userDetailRequest.getStream()));
                student.setStream(stream);
            }
            // Update the student's fields with new values from the request
            student.setRegistration_number(userDetailRequest.getRegistrationNumber());
            student.setFirstName(userDetailRequest.getFirstName());
            student.setLastName(userDetailRequest.getLastName());
            student.setEmail(userDetailRequest.getEmail());
            student.setMobileNumber(userDetailRequest.getMobileNumber());
            student.setExaminYear(userDetailRequest.getExaminationYear());
            student.setGender(userDetailRequest.getGender());
            student.setSchool(userDetailRequest.getSchool());
            student.setAddress(address); // Update the associated address
            // Save the updated student record
            Student updatedStudent = studentRepository.save(student);
            // Convert updated student entity to response DTO
            UserDetailsResponse dto = UserDetailMapper.INSTANCE.toDto(updatedStudent);
            dto.setUserRoles(loggedUserDetails.getUserRoles());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user details", e);
        }
    }
}
