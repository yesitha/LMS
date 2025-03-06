package com.itgura.service;

import com.itgura.exception.ValueNotExistException;
import com.itgura.response.dto.StreamResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserStreamService {

    String createStream(String stream);
    String deleteStream(UUID id);

    String updateStream(UUID id, String stream);
    StreamResponseDto getStreamById(UUID id) throws ValueNotExistException;
    List<StreamResponseDto> getAllStreams() throws ValueNotExistException;
}