package com.itgura.service.impl;

import com.itgura.dao.UserDao;
import com.itgura.entity.Address;
import com.itgura.entity.Stream;
import com.itgura.entity.Student;
import com.itgura.exception.ApplicationException;
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
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
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
    @Transactional
    public UserDetailsResponse findUser() {
        try{
            UserResponseDto loggedUserDetails = getLoggedUserDetails(UserUtil.extractToken());

            UUID userId = loggedUserDetails.getUserId();

            Student student = studentRepository.findByUserId(userId)
                    .orElseThrow(() -> {

                return new ValueNotFoundException("user not found id: " + userId);
            });

            UserDetailsResponse dto = UserDetailMapper.INSTANCE.toDto(student);
//            if(student.getProfilePicture()!=null) {
//                dto.setProfilePicture(Base64.getEncoder().encodeToString(ArrayUtils.toPrimitive(student.getProfilePicture())));
//            }else {
//                dto.setProfilePicture(null);
//            }

            dto.setUserRoles(loggedUserDetails.getUserRoles());
            return dto;

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetailsResponse registerUser(UserDetailRequest userDetailRequest) {
        try{

            Student student = new Student();
            student.setFirstName(userDetailRequest.getFirstName());
            student.setLastName(userDetailRequest.getLastName());
            student.setEmail(userDetailRequest.getEmail());
            student.setUserId(userDetailRequest.getUserID());

            if(userDetailRequest.getAddress()!=null) {
                Address address = new Address();
                address.setHouseNameOrNumber(userDetailRequest.getAddress().getHouseNameOrNumber());
                address.setLine1(userDetailRequest.getAddress().getLine1());
                address.setLine2(userDetailRequest.getAddress().getLine2());
                address.setCity(userDetailRequest.getAddress().getCity());
                Address savedAddress = addressRepository.save(address);
                student.setAddress(savedAddress);

            }
            if(userDetailRequest.getStream()!=null) {

                Stream stream = streamRepository.findById(userDetailRequest.getStream())
                        .orElseThrow(() -> {

                            return new ValueNotFoundException("stream not found id: " + userDetailRequest.getStream());
                        });
                student.setStream(stream);
            }
            if(userDetailRequest.getRegistrationNumber()==0) {
                // Fetch the maximum registration number from the database
                Integer maxRegistrationNumber = studentRepository.findMaxRegistrationNumber();

                // Increment the registration number (default to 1 if no records exist)
                int newRegistrationNumber = (maxRegistrationNumber != null) ? maxRegistrationNumber + 1 : 1;

                student.setRegistration_number(newRegistrationNumber);
            }else {
                student.setRegistration_number(userDetailRequest.getRegistrationNumber());
            }


            if(userDetailRequest.getMobileNumber()!=null) {
                student.setMobileNumber(userDetailRequest.getMobileNumber());
            }

            student.setExaminYear(userDetailRequest.getExaminationYear());

            if(userDetailRequest.getGender()!=null) {
                student.setGender(userDetailRequest.getGender());
            }
            if(userDetailRequest.getSchool()!=null) {
                student.setSchool(userDetailRequest.getSchool());
            }

            System.out.println(student);

            Student save = studentRepository.save(student);
            UserDetailsResponse dto = UserDetailMapper.INSTANCE.toDto(save);
            dto.setUserRoles("STUDENT");
            return dto;

        }catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

//    private String generateRegistrationNumber() {
//        // Step 1: Fetch the last registration number from the database
//        String lastRegistrationNumber = studentRepository.findLastRegistrationNumber(); // Custom query
//
//        // Step 2: Define default starting registration number if none exists
//        if (lastRegistrationNumber == null || lastRegistrationNumber.isEmpty()) {
//            return "AAA1"; // Start with the first registration number
//        }
//
//        // Step 3: Split the alphabetic and numeric parts
//        String alphabetPart = lastRegistrationNumber.replaceAll("\\d", ""); // Extract letters
//        String numericPart = lastRegistrationNumber.replaceAll("\\D", ""); // Extract numbers
//
//        // Step 4: Increment the numeric part
//        int number = Integer.parseInt(numericPart) + 1;
//
//        // Step 5: Handle rollover of numeric part
//        if (number > 1000) { // Define your maximum threshold
//            number = 1; // Reset to 1
//            alphabetPart = incrementAlphabet(alphabetPart); // Increment alphabet part
//        }
//
//        // Step 6: Format the new registration number
//        return alphabetPart + number;
//    }
//
//    // Helper method to increment the alphabetic part
//    private String incrementAlphabet(String alphabetPart) {
//        char[] chars = alphabetPart.toCharArray();
//        for (int i = chars.length - 1; i >= 0; i--) {
//            if (chars[i] < 'Z') {
//                chars[i]++;
//                break;
//            } else {
//                chars[i] = 'A'; // Reset to 'A' if at 'Z'
//            }
//        }
//        return new String(chars);
//    }
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

    @Override
    @Transactional
    public String updateProfilePicture(MultipartFile file) throws BadRequestRuntimeException, ValueNotFoundException {
        try {
            if (file == null) {
                throw new BadRequestRuntimeException("Profile picture is required");
            }

            String ext = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
            if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
                throw new BadRequestRuntimeException("Profile picture must be in JPG, JPEG, or PNG format");
            }

            UserResponseDto loggedUserDetails = getLoggedUserDetails(UserUtil.extractToken());
            UUID userId = loggedUserDetails.getUserId();

            // Find the existing Student record associated with the logged-in user

            if (!studentRepository.existsById(userId)) {
                 new ValueNotFoundException("User not found with ID: " + userId);
            }
                // Convert the MultipartFile to a byte array

            studentRepository.updateProfilePicture(userId,ArrayUtils.toObject(file.getBytes()));
            studentRepository.updateProfilePictureName(userId,file.getOriginalFilename());

                return "Profile picture updated successfully";

            } catch(ValueNotFoundException e){
                throw e;
            }catch(IllegalArgumentException e){
                throw new BadRequestRuntimeException(e.getMessage());

            } catch(BadRequestRuntimeException e){
                throw e;
            } catch(Exception e){
                throw new RuntimeException("Failed to update profile picture", e);

            }
        }


    }
