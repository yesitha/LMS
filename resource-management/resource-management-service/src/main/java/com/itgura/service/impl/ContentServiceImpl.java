package com.itgura.service.impl;

import com.itgura.entity.Content;
import com.itgura.entity.ContentTag;
import com.itgura.entity.Tag;
import com.itgura.repository.ContentRepository;
import com.itgura.repository.ContentTagRepository;
import com.itgura.repository.TagRepository;
import com.itgura.response.dto.TagResponseDto;
import com.itgura.service.ContentService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContentTagRepository contentTagRepository;






    @Override
    public String updateTags(UUID contentId, List<String> tags) {
        if(contentRepository.findById(contentId).isEmpty()){
           throw new RuntimeException("Content not found");
        }
        //delete existing tags
        List<ContentTag> contentTags = contentTagRepository.findByContentTagId(contentId);
        if(contentTags.size() > 0){
            contentTagRepository.deleteAll(contentTags);
        }

        for(String tag : tags) {
            if (tagRepository.existByName(tag)) {
                Tag existingTag = tagRepository.findByTagName(tag);
                ContentTag contentTag = new ContentTag();
                contentTag.setTag(existingTag);
                Content content = contentRepository.findById(contentId).orElse(null);
                contentTag.setContent(content);
                contentTagRepository.save(contentTag);
                contentRepository.save(content);

            } else {
                Tag newTag = new Tag();
                newTag.setTagName(tag);
                tagRepository.save(newTag);
                ContentTag contentTag = new ContentTag();
                contentTag.setTag(newTag);
                Content content = contentRepository.findById(contentId).orElse(null);
                contentTag.setContent(content);
                contentRepository.save(content);

                contentTagRepository.save(contentTag);

            }

        }
        return "Tags updated successfully";
    }

    @Override
    public List<TagResponseDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        for(Tag tag : tags){
            TagResponseDto tagResponseDto = new TagResponseDto();
            tagResponseDto.setTagId(tag.getTagId());
            tagResponseDto.setTagName(tag.getTagName());
            tagResponseDtos.add(tagResponseDto);

        }
        return tagResponseDtos;
    }

    @Override
    public String updateAccessTimeDuration(UUID contentId, Integer accessTimeDuration) {
        Content content = contentRepository.findById(contentId).
                orElseThrow(() -> new RuntimeException("Content not found"));
        content.setContentAccessTimeDuration(accessTimeDuration);
        contentRepository.save(content);
        return "Access time duration updated successfully";
    }

    @Override
    public Integer getAccessTimeDuration(UUID contentId) {
        Content content = contentRepository.findById(contentId).
                orElseThrow(() -> new NotFoundException("Content not found"));
        if(content.getContentAccessTimeDuration()!=null && content.getContentAccessTimeDuration()>0){
            return content.getContentAccessTimeDuration();
        }else{
            return null;
        }
    }

    @Override
    public Double getPrice(UUID contentId) {
        Content content = contentRepository.findById(contentId).
                orElseThrow(() -> new RuntimeException("Content not found"));
        return content.getPrice();
    }


}
