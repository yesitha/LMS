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
public class Session {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id")
    private UUID sessionId;
    @Column(name = "session_name")
    private String sessionName;
    @Column(name = "topic")
    private String  topic;
    @Column(name = "short_description")
    private String  shortDescription;
    @Column(name = "date_and_time")
    private Date dateAndTime;
    @OneToMany(mappedBy = "session",fetch = FetchType.EAGER)
    private List<Recording> classStudentList;
    @OneToMany(mappedBy = "session",fetch = FetchType.EAGER)
    private List<Material> lessonList;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "session",fetch = FetchType.EAGER)
    private List<ScheduleSession> scheduleSessionList;
    @OneToMany(mappedBy = "session",fetch = FetchType.EAGER)
    private List<StudentSession> studentSessionList;

}
