package com.itgura.service;

import com.itgura.entity.Content;
import com.itgura.response.dto.TagResponseDto;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    Content getContentById(UUID id);

    String updateTags(UUID contentId, List<String> tags);


    List<TagResponseDto> getAllTags();

    String updateAccessTimeDuration(UUID contentId, Integer accessTimeDuration);

    Integer getAccessTimeDuration(UUID contentId);

    Double getPrice(UUID contentId);
}
