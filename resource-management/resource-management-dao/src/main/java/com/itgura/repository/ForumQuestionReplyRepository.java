package com.itgura.repository;

import com.itgura.entity.ForumImage;
import com.itgura.entity.ForumQuestion;
import com.itgura.entity.ForumQuestionReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ForumQuestionReplyRepository extends JpaRepository<ForumQuestionReply, UUID> {
    List<ForumQuestionReply> findByForumQuestion(ForumQuestion forumQuestion);
}
