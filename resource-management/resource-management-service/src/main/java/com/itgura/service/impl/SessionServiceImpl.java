package com.itgura.service.impl;

import com.itgura.dao.ContentDao;
import com.itgura.entity.*;
import com.itgura.enums.ContentAccessType;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.*;
import com.itgura.request.SessionRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.MaterialResponseDto;
import com.itgura.response.dto.SessionResponseDto;
import com.itgura.response.dto.mapper.ClassMapper;
import com.itgura.response.dto.mapper.SessionMapper;
import com.itgura.response.hasPermissionResponse;
import com.itgura.service.ContentPermissionService;
import com.itgura.service.SessionService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ContentPermissionService contentPermissionService;

    @Override
    @Transactional
    public String createSession( UUID lessonId, SessionRequest request) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if(loggedUserDetails == null){
                throw new ValueNotExistException("User not found");
            }
            if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
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
                session.setSessionNumber(request.getSessionNumber());
                session.setIsAvailableForStudents(request.getIsAvailableForStudent());
                session.setDateAndTime(request.getStartDateAndTime());
                session.setCreatedBy(userId);
                session.setCreatedOn(new Date(System.currentTimeMillis()));
                session.setLastModifiedOn(new Date(System.currentTimeMillis()));
                session.setLastModifiedBy(userId);
                session.setContentAccessType(request.getContentAccesstype());
                Session save = sessionRepository.save(session);
                addSessionToSchedule(save.getLesson().getAClass().getContentId(),save,true);
                if(session.getIsAvailableForStudents()) {
//                givePermissionToStudents();
                }
                return "Session saved successfully";

            }else {
                throw new ForbiddenException("User is not authorized to perform this operation");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public SessionResponseDto findSessionById( UUID sessionId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());

            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
                SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                dto.setIsAvailableForLoggedUser(true);
                return dto;
            } else {
                Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
                Boolean b = false;
                if (session.getContentAccessType() == ContentAccessType.PUBLIC) {
                    b = true;
                }
                ArrayList<UUID> objects = new ArrayList<>();
                objects.add(session.getContentId());
                List<hasPermissionResponse> hasPermissionResponses = contentPermissionService.hasPermissionForContentList(objects);
                if (hasPermissionResponses.get(0).getHasPermission()) {
                    b = true;
                }
                if (b) {
                    SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                    List<MaterialResponseDto> materialList = dto.getMaterialList();

                    // Remove objects where isAvailableForStudents is false
                    materialList.removeIf(material -> !material.getIsAvailableForStudents());
                    dto.setIsAvailableForLoggedUser(true);
                    return dto;
                } else {
                    SessionResponseDto dto = new SessionResponseDto();
                    dto.setId(session.getContentId());
                    dto.setSessionName(session.getSessionName());
                    dto.setTopic(session.getTopic());
                    dto.setDateAndTime(session.getDateAndTime());
                    dto.setIsAvailableForLoggedUser(false);
                    return dto;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<SessionResponseDto> findAllSession( UUID lessonId) throws CredentialNotFoundException, BadRequestRuntimeException, ApplicationException, URISyntaxException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ValueNotExistException("Lesson not found with id " + lessonId));


            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                ArrayList<Session> allByLesson = sessionRepository.findAllByLesson(lesson);
                ArrayList<SessionResponseDto> sessionResponseDtos = new ArrayList<>();
                for (Session session : allByLesson) {
                    SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                    dto.setIsAvailableForLoggedUser(true);
                    sessionResponseDtos.add(dto);
                }

                // Sort the sessionResponseDtos by sessionNumber
                sessionResponseDtos.sort(Comparator.comparingInt(SessionResponseDto::getSessionNumber));

                return sessionResponseDtos;

            } else {

                ArrayList<Session> allByLesson = sessionRepository.findAllByLesson(lesson);
                List<UUID> sessionIds = allByLesson.stream()
                        .map(Session::getContentId)
                        .collect(Collectors.toList());

                List<hasPermissionResponse> hasPermissionResponses = contentPermissionService.hasPermissionForContentList(sessionIds);

                // Create a map from contentId to hasPermission
                Map<UUID, Boolean> permissionMap = hasPermissionResponses.stream()
                        .collect(Collectors.toMap(hasPermissionResponse::getContentId, hasPermissionResponse::getHasPermission));

                ArrayList<SessionResponseDto> sessionResponseDtos = new ArrayList<>();

                for (Session session : allByLesson) {
                    Boolean haveAccess = false;
                    if (session.getContentAccessType() == ContentAccessType.PUBLIC) {
                        haveAccess = true;
                    } else {
                        // Use the map to check for permission
                        Boolean hasPermission = permissionMap.get(session.getContentId());
                        if (hasPermission != null && hasPermission) {
                            haveAccess = true;
                        }
                    }

                    if (haveAccess) {
                        if (session.getIsAvailableForStudents()) {
                            SessionResponseDto dto = SessionMapper.INSTANCE.toDto(session);
                            List<MaterialResponseDto> materialList = dto.getMaterialList();

                            // Remove objects where isAvailableForStudents is false
                            materialList.removeIf(material -> !material.getIsAvailableForStudents());

                            dto.setIsAvailableForLoggedUser(true);
                            sessionResponseDtos.add(dto);
                        }
                    } else {
                        if (session.getIsAvailableForStudents()) {
                            SessionResponseDto dto = new SessionResponseDto();
                            dto.setId(session.getContentId());
                            dto.setSessionName(session.getSessionName());
                            dto.setTopic(session.getTopic());
                            dto.setDateAndTime(session.getDateAndTime());
                            dto.setIsAvailableForLoggedUser(false);
                            sessionResponseDtos.add(dto);
                        }
                    }
                }

                return sessionResponseDtos;

            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public String updateSession(UUID sessionId, SessionRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
                UUID userId = loggedUserDetails.getUserId();
                Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ValueNotExistException("Session not found with id " + sessionId));
                session.setSessionName(request.getSessionName());
                session.setShortDescription(request.getDescription());
                session.setTopic(request.getTopic());
                session.setSessionNumber(request.getSessionNumber());
                session.setIsAvailableForStudents(request.getIsAvailableForStudent());
                session.setDateAndTime(request.getStartDateAndTime());
                session.setLastModifiedOn(new Date(System.currentTimeMillis()));
                session.setLastModifiedBy(userId);
                session.setContentAccessType(request.getContentAccesstype());
                Session save = sessionRepository.save(session);
                addSessionToSchedule(save.getLesson().getAClass().getContentId(), save, false);
                if (session.getIsAvailableForStudents()) {
//                givePermissionToStudents();
                }
                return "Session updated successfully";

            } else {
                throw new ForbiddenException("User is not authorized to perform this operation");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addSessionToSchedule(UUID classId,Session session,Boolean isNew) throws ValueNotExistException {
        AClass aClass = classRepository.findById(classId).orElseThrow(() -> new ValueNotExistException("Class not found with id " + classId));
        Schedule byAClassContentId = scheduleRepository.findScheduleByAClass(aClass);
        if(session.getIsAvailableForStudents() && isNew) {


            if (byAClassContentId == null) {
                Schedule schedule = new Schedule();
                schedule.setAClass(aClass);
                schedule.setScheduleName("Schedule for  "+ aClass.getYear()+ " - " +aClass.getClassName());

                byAClassContentId = scheduleRepository.save(schedule);
            }
            ScheduleSession scheduleSession = new ScheduleSession();
            scheduleSession.setDateAndTime(session.getDateAndTime());
            scheduleSession.setSession(session);
            scheduleSession.setShortDescription(session.getShortDescription());
            scheduleSession.setSchedule(byAClassContentId);
            scheduleSession.setSession(session);
            ScheduleSession save = scheduleSessionRepositoryRepository.save(scheduleSession);
            return save != null;
        }else if(session.getIsAvailableForStudents() && !isNew) {
            if (byAClassContentId == null) {
                Schedule schedule = new Schedule();
                schedule.setAClass(aClass);
                schedule.setScheduleName("Schedule for  " + aClass.getYear() + " - " + aClass.getClassName());

                byAClassContentId = scheduleRepository.save(schedule);
            }
            List<ScheduleSession> bySessionId = scheduleSessionRepositoryRepository.findBySessionId(session.getContentId());
            if (bySessionId.size() > 0) {
                for (ScheduleSession scheduleSession : bySessionId) {
                    scheduleSession.setDateAndTime(session.getDateAndTime());
                    scheduleSession.setShortDescription(session.getShortDescription());
                    scheduleSession.setSchedule(byAClassContentId);
                    scheduleSession.setSession(session);
                    scheduleSessionRepositoryRepository.save(scheduleSession);
                }
            }
            return true;
        }
        return false;

    }

    private void givePermissionToStudents() {


    }

}
