package com.itgura.service.impl;

import com.itgura.entity.Lesson;
import com.itgura.entity.Session;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.LessonRepository;
import com.itgura.repository.SessionRepository;
import com.itgura.request.SessionRequest;
import com.itgura.service.SessionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    @Transactional
    public String createSession(UUID lessonId, SessionRequest request) {
        try{
            // TODO : get user id from security context
            UUID userId = null;
            Lesson lesson;
            Optional<Lesson> byId = lessonRepository.findById(lessonId);
            if (byId.isPresent()) {
                lesson = byId.get();
            } else {
                throw new ValueNotExistException("Lesson not found with id " +lessonId);
            }
            Session session = new Session();
            session.setLesson(lesson);
            session.setSessionName(request.getSessionName());
            session.setShortDescription(request.getDescription());
            session.setTopic(request.getTopic());
            session.setIsAvailableForStudents(false);
            session.setDateAndTime(request.getStartDateAndTime());
            session.setCreatedBy(userId);
            session.setCreatedOn(new Date(System.currentTimeMillis()));
            session.setLastModifiedOn(new Date(System.currentTimeMillis()));
            session.setLastModifiedBy(userId);
            sessionRepository.save(session);
            return "Session saved successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateSession(UUID sessionId, SessionRequest request) {
        return null;
    }

    @Override
    public String deleteSession(UUID sessionId) {
        return null;
    }

    @Override
    public String findSession(UUID sessionId) {
        return null;
    }

    @Override
    public String findAllSession(UUID lessonId) {
        return null;
    }
}
