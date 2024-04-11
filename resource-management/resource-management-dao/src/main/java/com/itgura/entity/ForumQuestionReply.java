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
public class ForumQuestionReply {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forum_question_reply_id")
    private UUID forumQuestionReplyId;
    @Column(name = "reply", columnDefinition = "TEXT")
    private String reply;
    @Column(name = "created_by")
    @Lob
    private UUID createdBy;
    @Column(name = "created_on")
    private java.util.Date createdOn;
    @ManyToOne
    @JoinColumn(name = "forum_question_id")
    private ForumQuestion forumQuestion;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private ForumQuestionReply parentReply;

    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL)
    private List<ForumQuestionReply> childReplies ;

}
