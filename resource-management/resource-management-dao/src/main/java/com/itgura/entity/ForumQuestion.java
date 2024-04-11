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
public class ForumQuestion {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forum_question_id")
    private UUID forumQuestionId;
    @Column(name = "question", columnDefinition = "TEXT")
    private String question;
    @Column(name = "created_by")
    @Lob
    private UUID createdBy;
    @Column(name = "created_on")
    private java.util.Date createdOn;
    @Column(name = "person_name")
    private String personName;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @OneToMany(mappedBy = "forumQuestion",fetch = FetchType.EAGER)
    private List<ForumQuestionReply> forumQuestionReplyList;

}
