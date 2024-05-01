package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "forum", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "forum_id")
public class Forum extends Content{



    @Column(name = "announcement", columnDefinition = "TEXT")
    private String announcement;
    @Column(name = "created_by_name")
    private String createdByName;
    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "forum")
    private List<ForumQuestion> forumQuestionList;
}
