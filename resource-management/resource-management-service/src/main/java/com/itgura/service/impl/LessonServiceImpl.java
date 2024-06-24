package com.itgura.service.impl;

import com.itgura.entity.AClass;
import com.itgura.entity.Lesson;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.repository.LessonRepository;
import com.itgura.request.LessonRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.LessonResponseDto;
import com.itgura.response.dto.mapper.LessonMapper;
import com.itgura.service.LessonService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    @Transactional
    public String saveLesson(LessonRequest request) throws ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }
            if(!(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER"))){
                throw new ForbiddenException("User is not authorized to perform this operation");
            }else {
                UUID userId = loggedUserDetails.getUserId();

                Lesson lesson = new Lesson();
                lesson.setLessonName(request.getLessonName());
                lesson.setLessonDescription(request.getDescription());
                lesson.setLessonNumber(request.getLessonNumber());
                lesson.setStartDate(request.getStartDate());
                lesson.setEndDate(request.getEndDate());
                Optional<AClass> byId = classRepository.findById(request.getClassId());
                if (byId.isPresent()) {
                    lesson.setAClass(byId.get());
                } else {
                    throw new ValueNotExistException("Class not found with id " + request.getClassId());
                }
                lesson.setPrice(request.getPrice());
                lesson.setIsAvailableForStudents(request.getIsAvailableForStudents());
                lesson.setCreatedOn(new Date(System.currentTimeMillis()));
                lesson.setLastModifiedOn(new Date(System.currentTimeMillis()));
                lesson.setCreatedBy(userId);
                lesson.setLastModifiedBy(userId);
                lesson.setContentAccessType(request.getContentAccesstype());
                lessonRepository.save(lesson);
                return "Lesson saved successfully";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String updateLesson(LessonRequest request, UUID id) throws ValueNotExistException {
        try{
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }
            if(!(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER"))){
                throw new ForbiddenException("User is not authorized to perform this operation");
            }else {
                UUID userId = loggedUserDetails.getUserId();


                Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));

                if (request.getLessonName() != null && !request.getLessonName().isEmpty()) {
                    lesson.setLessonName(request.getLessonName());
                }
                if (request.getLessonNumber() != null) {
                    lesson.setLessonNumber(request.getLessonNumber());
                }
                if (request.getDescription() != null) {
                    lesson.setLessonDescription(request.getDescription());
                }
                if (request.getStartDate() != null) {
                    lesson.setStartDate(request.getStartDate());
                }
                if (request.getEndDate() != null) {
                    lesson.setEndDate(request.getEndDate());
                }
                if (request.getLessonDuration() != null) {
                    lesson.setLessonDuration(request.getLessonDuration());
                }
                if (request.getIsAvailableForStudents() != null) {
                    lesson.setIsAvailableForStudents(request.getIsAvailableForStudents());
                }
                if (request.getPrice() != null) {
                    lesson.setPrice(request.getPrice());
                }
                if (request.getContentAccesstype() != null) {
                    lesson.setContentAccessType(request.getContentAccesstype());
                }
                if (request.getClassId() != null) {
                    Optional<AClass> classOptional = classRepository.findById(request.getClassId());
                    if (classOptional.isPresent()) {
                        lesson.setAClass(classOptional.get());
                    } else {
                        throw new ValueNotExistException("Class not found with id " + request.getClassId());
                    }
                }

                lesson.setLastModifiedOn(new Date(System.currentTimeMillis()));
                lesson.setLastModifiedBy(userId);
                lessonRepository.save(lesson);
                return "Lesson patched successfully ";
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String deleteLesson(UUID id) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                try {
                    lessonRepository.deleteById(id);
                    return "Lesson deleted successfully with id " + id;
                } catch (Exception e) {
                    throw new ApplicationException("Error while deleting lesson with id " + id);
                }

            } else {
                throw new ForbiddenException("User is not authorized to perform this operation");

            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    @Transactional
    public LessonResponseDto findLesson( UUID id) throws ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            } else {
                Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));
                if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {

                    return LessonMapper.INSTANCE.toDto(lesson);
                } else {

                    if (lesson.getIsAvailableForStudents()) {
                        return LessonMapper.INSTANCE.toDto(lesson);
                    } else {
                        throw new ForbiddenException("User is not authorized to perform this operation");
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<LessonResponseDto> findAllLesson(UUID classId) throws CredentialNotFoundException, BadRequestRuntimeException,ValueNotExistException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }else {
                if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
                    List<Lesson> lessons = lessonRepository.findAll();
                    List<LessonResponseDto> dtoList = LessonMapper.INSTANCE.toDtoList(lessons);
                    dtoList.sort(Comparator.comparing(LessonResponseDto::getLessonNumber));
                    return dtoList;
                }else{
                    List<Lesson> lessons = lessonRepository.findAll();
                    List<LessonResponseDto> dtoList = LessonMapper.INSTANCE.toDtoList(lessons);
                    dtoList = dtoList.stream()
                            .filter(LessonResponseDto::getIsAvailableForAllStudents)
                            .collect(Collectors.toList());

                    // Sort the filtered list based on lessonNumber
                    dtoList.sort(Comparator.comparing(LessonResponseDto::getLessonNumber));
                    return dtoList;

                }

            }
        }catch (Exception e){
                throw new RuntimeException(e);
        }


    }
}
