package com.itgura.service.impl;

import com.itgura.entity.AClass;
import com.itgura.entity.Announcement;
import com.itgura.entity.Lesson;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.AnnouncementRepository;
import com.itgura.repository.ClassRepository;
import com.itgura.request.AnnouncementRequest;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.AnnouncementResponseDto;
import com.itgura.response.dto.mapper.AnnouncementMapper;
import com.itgura.service.AnnouncementService;
import com.itgura.service.UserDetailService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private ClassRepository classRepository;
    @Override
    @Transactional
    public String createAnnouncement(UUID classId, AnnouncementRequest request) throws ApplicationException, URISyntaxException, CredentialNotFoundException, BadRequestRuntimeException {
        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
        if(loggedUserDetails == null){
            throw new ValueNotExistException("User not found");
        }
        if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
            Announcement announcement = new Announcement();

            announcement.setAnnouncementTitle(request.getTitle());
            announcement.setAnnouncementDescription(request.getDescription());
            AClass cl = classRepository.findById(classId).orElseThrow(() -> new ValueNotExistException("class not found with id " + classId));
            announcement.setAClass(cl);
            announcement.setAnnouncementOn(new Date(System.currentTimeMillis()));
            UUID userId = loggedUserDetails.getUserId();
            announcement.setAnnouncementBy(userId);
            announcementRepository.save(announcement);
            return "Announcement created successfully";


        }else{
            throw new ApplicationException("User is not authorized to perform this operation");
        }
    }

    @Override
    @Transactional
    public AnnouncementResponseDto findAnnouncementById(UUID announcementId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ValueNotExistException("Announcement not found with id " + announcementId));
        return AnnouncementMapper.INSTANCE.toDto(announcement);
    }

    @Override
    @Transactional
    public List<AnnouncementResponseDto> findAllAnnouncement(UUID classId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {

        List<Announcement> announcements = announcementRepository.findAllByAClass(classId);
        List<AnnouncementResponseDto> dtoList = AnnouncementMapper.INSTANCE.toDtoList(announcements);

        return dtoList;
    }

    @Override
    @Transactional
    public String updateAnnouncement(UUID announcementId, AnnouncementRequest request) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {
        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
        if(loggedUserDetails == null){
            throw new ValueNotExistException("User not found");
        }
        if(loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ValueNotExistException("Announcement not found with id " + announcementId));
        announcement.setAnnouncementTitle(request.getTitle());
        announcement.setAnnouncementDescription(request.getDescription());
        announcementRepository.save(announcement);
        return "Announcement updated successfully";

        }else{
            throw new ApplicationException("User is not authorized to perform this operation");
        }
    }

    @Override
    @Transactional
    public String deleteAnnouncement(UUID announcementId) throws ApplicationException, CredentialNotFoundException, BadRequestRuntimeException, URISyntaxException {
        UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
        if (loggedUserDetails == null) {
            throw new ValueNotExistException("User not found");
        }
        if (loggedUserDetails.getUserRoles().equals("ADMIN") || loggedUserDetails.getUserRoles().equals("TEACHER")) {
            Announcement announcement = announcementRepository.findById(announcementId)
                    .orElseThrow(() -> new ValueNotExistException("Announcement not found with id " + announcementId));
            announcementRepository.delete(announcement);
            return "Announcement deleted successfully";

        } else {
            throw new ApplicationException("User is not authorized to perform this operation");
        }
    }


}
