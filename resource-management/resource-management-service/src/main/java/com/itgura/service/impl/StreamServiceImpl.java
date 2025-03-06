package com.itgura.service.impl;

import com.itgura.entity.Stream;
import com.itgura.exception.ValueNotExistException;
import com.itgura.repository.StreamRepository;
import com.itgura.request.dto.UserResponseDto;
import com.itgura.response.dto.StreamResponseDto;
import com.itgura.response.dto.mapper.StreamMapper;
import com.itgura.service.UserDetailService;
import com.itgura.service.UserStreamService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StreamServiceImpl implements UserStreamService {
    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    @Transactional
    public String createStream(String stream) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (!(loggedUserDetails.getUserRoles().equals("ADMIN"))) {
                throw new ForbiddenException("User is not authorized to perform this operation");
            } else {
                Stream stream1 = new Stream();
                stream1.setStream(stream);
                streamRepository.save(stream1);
                return "Stream saved successfully";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String deleteStream(UUID id) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (!(loggedUserDetails.getUserRoles().equals("ADMIN"))) {
                throw new ForbiddenException("User is not authorized to perform this operation");
            } else {
                Optional<Stream> byId = streamRepository.findById(id);
                if (byId.isPresent()) {
                    streamRepository.deleteById(id);
                    return "Stream deleted successfully";
                } else {
                    return "Stream not found with id: " + id;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String updateStream(UUID id, String stream) {
        try {
            UserResponseDto loggedUserDetails = userDetailService.getLoggedUserDetails(UserUtil.extractToken());
            if (loggedUserDetails == null) {
                throw new ValueNotExistException("User not found");
            }
            if (!(loggedUserDetails.getUserRoles().equals("ADMIN"))) {
                throw new ForbiddenException("User is not authorized to perform this operation");
            } else {
                Optional<Stream> byId = streamRepository.findById(id);
                if (byId.isPresent()) {
                    Stream stream1 = byId.get();
                    stream1.setStream(stream);
                    streamRepository.save(stream1);
                    return "Stream updated successfully";
                } else {
                    return "Stream not found with id: " + id;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public StreamResponseDto getStreamById(UUID id) throws ValueNotExistException {
        Optional<Stream> byId = streamRepository.findById(id);
        if (byId.isPresent()) {
            Stream stream1 = byId.get();
            return StreamMapper.INSTANCE.toDto(stream1);
        } else {
            throw new ValueNotExistException("Stream not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<StreamResponseDto> getAllStreams() throws ValueNotExistException {
        List<Stream> all = streamRepository.findAll();
        if (all.isEmpty()) {
            throw new ValueNotExistException("No streams found");
        } else {
            return StreamMapper.INSTANCE.toDtoList(all);
        }
    }
}