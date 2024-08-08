package com.itgura.response.dto.mapper;

import com.itgura.entity.ForumQuestion;
import com.itgura.response.dto.ForumQuestionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ForumImageMapper.class})
public interface ForumQuestionMapper {
    ForumQuestionMapper INSTANCE = Mappers.getMapper(ForumQuestionMapper.class);

    @Mapping(source = "contentId", target = "id")

    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    @Mapping(target = "numberOfReplies", expression = "java(forumQuestion.getForumQuestionReplyList().size())")
    ForumQuestionResponseDto toDto(ForumQuestion forumQuestion);

    List<ForumQuestionResponseDto> toDtoList(List<ForumQuestion> forumQuestions);
}
