package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "forum_question", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "forum_question_id")
public class ForumQuestion extends Content {

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "person_name")
    private String personName;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @OneToMany(mappedBy = "forumQuestion")
    private List<ForumQuestionReply> forumQuestionReplyList;

}
