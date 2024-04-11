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
public class Forum {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forum_id")
    private UUID forumId;
    @Column(name = "created_by")
    @Lob
    private UUID createdBy;
    @Column(name = "created_on")
    private java.util.Date createdOn;
    @Column(name = "announcement", columnDefinition = "TEXT")
    private String announcement;
    @Column(name = "created_by_name")
    private String createdByName;
    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "forum",fetch = FetchType.EAGER)
    private List<ForumQuestion> forumQuestionList;
}
