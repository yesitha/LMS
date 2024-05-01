package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "forum_question_reply", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "forum_question_reply_id")
public class ForumQuestionReply extends Content{

    @Column(name = "reply", columnDefinition = "TEXT")
    private String reply;

    @ManyToOne
    @JoinColumn(name = "forum_question_id")
    private ForumQuestion forumQuestion;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private ForumQuestionReply parentReply;

    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL)
    private List<ForumQuestionReply> childReplies ;

}
