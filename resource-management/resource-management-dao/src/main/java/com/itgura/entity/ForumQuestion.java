package com.itgura.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "forumQuestion", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ForumImage> forumImageList;

    @OneToMany(mappedBy = "forumQuestion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ForumQuestionReply> forumQuestionReplyList;

}
