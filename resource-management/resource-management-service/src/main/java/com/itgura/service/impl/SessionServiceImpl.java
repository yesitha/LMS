package com.itgura.service.impl;

import com.itgura.dao.ContentDao;
import com.itgura.entity.*;
import com.itgura.enums.ContentAccessType;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.*;
import com.itgura.request.SessionRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.response.dto.mapper.SessionMapper;
import com.itgura.service.SessionService;
import com.itgura.service.UserDetailService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private ContentDao contentDao;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ScheduleSessionRepositoryRepository scheduleSessionRepositoryRepository;

    @Override
    @Transactional
    public String createSession(String token, UUID lessonId, SessionRequest request) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(token);
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (!loggedUserDetails.getUserRoles().equals("ADMIN") || !loggedUserDetails.getUserRoles().equals("TEACHER")) {
                throw new ForbiddenException("User is not authorized to perform this operation");
            }
            UUID userId = loggedUserDetails.getUserId();
            Lesson lesson;
            Optional<Lesson> byId = lessonRepository.findById(lessonId);
            if (byId.isPresent()) {
                lesson = byId.get();
            } else {
                throw new ValueNotExistException("Lesson not found with id " + lessonId);
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
            session.setContentAccessType(request.getContentAccesstype());
            Session save = sessionRepository.save(session);
            addSessionToSchedule(save.getContentId(),save);
            givePermissionToStudents();
            return "Session saved successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SessionResponseDto findSessionById(String token, UUID sessionId) throws ValueNotExistException, CredentialNotFoundException, BadRequestRuntimeException {
        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(token);

        if (loggedUserDetails == null) {
            throw new ValueNotExistException("User not found");
        }
        if (!loggedUserDetails.getUserRoles().equals("ADMIN") || !loggedUserDetails.getUserRoles().equals("TEACHER")) {
            Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
            SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
            dto.setIsAvailableForLoggedUser(true);
            return dto;
        } else {
            Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
            Boolean b = contentRepository.checkStudentAvailableContent(session.getContentId(), loggedUserDetails.getUserId());
            if(session.getContentAccessType()== ContentAccessType.PUBLIC){
                b = true;
            }
            if (b) {
                SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                dto.setIsAvailableForLoggedUser(true);
                return dto;
            } else {
                SessionResponseDto dto = new SessionResponseDto();
                dto.setId(session.getContentId());
                dto.setSessionName(session.getSessionName());
                dto.setShortDescription(session.getShortDescription());
                dto.setTopic(session.getTopic());
                dto.setDateAndTime(session.getDateAndTime());
                dto.setIsAvailableForLoggedUser(false);
                dto.setCreatedByUserId(session.getCreatedBy());
                dto.setCreatedOn(session.getCreatedOn());
                dto.setLastModifiedByUserId(session.getLastModifiedBy());
                dto.setLastModifiedOn(session.getLastModifiedOn());
                return dto;
            }
        }
    }

    @Override
    @Transactional
    public List<SessionResponseDto> findAllSession(String token, UUID lessonId) throws CredentialNotFoundException, BadRequestRuntimeException, ValueNotExistException {
        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(token);
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + lessonId));


        if (loggedUserDetails == null) {
            throw new ValueNotExistException("User not found");
        }
        if (!loggedUserDetails.getUserRoles().equals("ADMIN") || !loggedUserDetails.getUserRoles().equals("TEACHER")) {
            ArrayList<Session> allByLesson = sessionRepository.findAllByLesson(lesson);
            ArrayList<SessionResponseDto> sessionResponseDtos = new ArrayList<>();
            for (Session session : allByLesson) {
                SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                dto.setIsAvailableForLoggedUser(true);
                sessionResponseDtos.add(dto);

            }
            return sessionResponseDtos;

        } else {
            ArrayList<Session> allByLesson = sessionRepository.findAllByLesson(lesson);
            ArrayList<SessionResponseDto> sessionResponseDtos = new ArrayList<>();
            for (Session session : allByLesson) {
                Boolean b = contentRepository.checkStudentAvailableContent(session.getContentId(), loggedUserDetails.getUserId());
                SessionResponseDto dto;
                if(session.getContentAccessType()== ContentAccessType.PUBLIC){
                    b = true;
                }
                if (b) {
                    dto = SessionMapper.INSTANCE.toDto(session);
                    dto.setIsAvailableForLoggedUser(true);
                } else {
                    dto = new SessionResponseDto();
                    dto.setId(session.getContentId());
                    dto.setSessionName(session.getSessionName());
                    dto.setShortDescription(session.getShortDescription());
                    dto.setTopic(session.getTopic());
                    dto.setDateAndTime(session.getDateAndTime());
                    dto.setIsAvailableForLoggedUser(false);
                    dto.setCreatedByUserId(session.getCreatedBy());
                    dto.setCreatedOn(session.getCreatedOn());
                    dto.setLastModifiedByUserId(session.getLastModifiedBy());
                    dto.setLastModifiedOn(session.getLastModifiedOn());
                }
                sessionResponseDtos.add(dto);

            }
            return sessionResponseDtos;
        }
    }

    @Override
    @Transactional
    public List<UUID> findAllSessionsInMonth(UUID classId, int month, int year) throws CredentialNotFoundException, BadRequestRuntimeException, ValueNotExistException {
        if (month < 1 || month > 12) {
            throw new BadRequestRuntimeException("Invalid month value: " + month);
        }
        if (year < 0) {
            throw new BadRequestRuntimeException("Invalid year value: " + year);
        }

        try {
            return sessionRepository.findAllSessionIdsInMonthAndClass(classId,month, year);
        } catch (Exception e) {
            throw new ValueNotExistException("Error fetching session IDs for the given month and year", e);
        }

    }

    private boolean addSessionToSchedule(UUID classId,Session session) throws ValueNotExistException {

        Schedule byAClassContentId = scheduleRepository.findByAClass(session.getContentId());
        AClass aClass = classRepository.findById(classId).orElseThrow(() -> new ValueNotExistException("Class not found with id " + classId));
        if (byAClassContentId == null) {
            Schedule schedule = new Schedule();
            schedule.setAClass(aClass);
            schedule.setScheduleName("Schedule for class " + aClass.getClassName()+ " - "+aClass.getYear());

            byAClassContentId = scheduleRepository.save(schedule);
        }
        ScheduleSession scheduleSession = new ScheduleSession();
        scheduleSession.setDateAndTime(session.getDateAndTime());
        scheduleSession.setSession(session);
        scheduleSession.setSchedule(byAClassContentId);
        scheduleSession.setSchedule(byAClassContentId);
        ScheduleSession save = scheduleSessionRepositoryRepository.save(scheduleSession);
        return save != null;

    }

    private void givePermissionToStudents() {

    }

}
