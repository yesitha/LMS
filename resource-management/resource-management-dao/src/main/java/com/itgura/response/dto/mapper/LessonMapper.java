package com.itgura.response.dto.mapper;

import com.itgura.entity.Lesson;
import com.itgura.response.dto.LessonResponseDto;

import java.util.List;

public class LessonMapper {
    public LessonResponseDto toDto(Lesson lesson){
        return LessonResponseDto.builder()
                .id(lesson.getContentId())
                .lessonName(lesson.getLessonName())
                .lessonNumber(lesson.getLessonNumber())
                .description(lesson.getLessonDescription())
                .startDate(lesson.getStartDate())
                .endDate(lesson.getEndDate())
                .lessonDuration(lesson.getLessonDuration())
                .classId(lesson.getAClass().getContentId())
                .price(lesson.getPrice())
                .isAvailableForAllUser(lesson.getIsAvailableForUsers())
                .createdOn(lesson.getCreatedOn())
                .createdByUserId(lesson.getCreatedBy())
                .lastModifiedByUserId(lesson.getLastModifiedBy())
                .createdOn(lesson.getCreatedOn())
                .lastModifiedOn(lesson.getLastModifiedOn())
                .build();
    }
    public List<LessonResponseDto> toDtoList(List<Lesson> lessons){
        return lessons.stream().map(this::toDto).toList();
    }
}
