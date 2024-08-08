package com.itgura.response.dto.mapper;

import com.itgura.entity.ForumQuestion;
import com.itgura.entity.ForumQuestionReply;
import com.itgura.entity.Lesson;
import com.itgura.response.dto.ForumQuestionReplyResponseDto;
import com.itgura.response.dto.ForumQuestionResponseDto;
import com.itgura.response.dto.LessonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ForumQuestionReplyMapper {
    ForumQuestionReplyMapper INSTANCE = Mappers.getMapper(ForumQuestionReplyMapper.class);

    @Mapping(source = "contentId", target = "id")

    @Mapping(source = "createdBy", target = "createdByUserId")
    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserId")
    @Mapping(source = "childReplies", target = "replyList")
    ForumQuestionReplyResponseDto toDto(ForumQuestionReply reply);
    List<ForumQuestionReplyResponseDto> toDtoList(List<ForumQuestionReply> forumQuestionReplyList);

}
