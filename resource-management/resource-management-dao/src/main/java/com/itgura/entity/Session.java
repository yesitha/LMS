package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "session", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "session_id")
public class Session extends Content {

    @Column(name = "session_name")
    private String sessionName;
    @Column(name = "topic")
    private String  topic;
    @Column(name = "short_description")
    private String  shortDescription;
    @Column(name = "date_and_time")
    private Date dateAndTime;
    @OneToMany(mappedBy = "session")
    private List<Recording> classStudentList;
    @OneToMany(mappedBy = "session")
    private List<Material> lessonList;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "session")
    private List<ScheduleSession> scheduleSessionList;
    @OneToMany(mappedBy = "session")
    private List<StudentTransactionContent> studentTransactionContentList;
    @Column(name = "is_available_for_students")
    private Boolean isAvailableForStudents;

}
