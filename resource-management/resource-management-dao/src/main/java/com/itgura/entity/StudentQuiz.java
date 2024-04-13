package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student_quiz", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentQuiz {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_quiz_id")
    private UUID studentQuizId;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "marks")
    private Double marks;
    @Column(name = "attempted_date")
    private java.sql.Date attemptedDate;
    @Column(name = "started_time")
    private java.sql.Time startedTime;
    @Column(name = "ended_time")
    private java.sql.Time endedTime;
    @Column(name = "duration_in_minutes")
    private Integer durationInMinutes;
    @OneToMany(mappedBy = "studentQuiz",fetch = FetchType.EAGER)
    private List<StudentAnswerQuiz> studentAnswerQuizList;
}
