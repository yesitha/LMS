package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "lesson", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Lesson extends Content {

    @Column(name = "lesson_name")
    private String lessonName;
    @Column(name = "lesson_number")
    private Integer lessonNumber;
    @Column(name = "lesson_description")
    private String lessonDescription;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "lesson_duration")
    private Integer lessonDuration;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private AClass aClass;
    @OneToMany(mappedBy = "lesson")
    private List<Session> sessionList;
    @OneToMany(mappedBy = "lesson")
    private List<Quiz> quizList;
}
