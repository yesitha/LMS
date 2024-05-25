package com.itgura.service.impl;

import com.itgura.entity.AClass;
import com.itgura.entity.Lesson;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.ClassRepository;
import com.itgura.repository.LessonRepository;
import com.itgura.request.LessonRequest;
import com.itgura.response.dto.LessonResponseDto;
import com.itgura.response.dto.mapper.LessonMapper;
import com.itgura.service.LessonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ClassRepository classRepository;

    @Override
    @Transactional
    public String saveLesson(LessonRequest request) throws ValueNotExistException {
        try {
            // TODO : get user id from security context
            UUID userId = null;

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
            lessonRepository.save(lesson);
            return "Lesson saved successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String updateLesson(LessonRequest request, UUID id) throws ValueNotExistException {
        try{
            // TODO : get user id from security context
            UUID userId = null;

            Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));

            if (request.getLessonName() != null && !request.getLessonName().isEmpty()) {
                lesson.setLessonName(request.getLessonName());
            }
            if (request.getLessonNumber() != null ) {
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
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteLesson(UUID id) throws ValueNotExistException {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));
        // TODO: check if lesson is associated with any user and delete

        return "Lesson deleted successfully with id " + id  ;
    }

    @Override
    public LessonResponseDto findLesson(UUID id) throws ValueNotExistException {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + id));
        // TODO: check if lesson is available for the users
        // TODO : set null unwanted data matching to the user
        return LessonMapper.INSTANCE.toDto(lesson);
    }

    @Override
    public List<LessonResponseDto> findAllLesson(UUID classId) {
        try {
            // TODO: check if lesson is available for the users and set it
            // TODO: check if lesson is available for the logged in user and set it
            // TODO : set null unwanted data matching to the user
            List<Lesson> lessons = lessonRepository.findAll();
            return LessonMapper.INSTANCE.toDtoList(lessons);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
