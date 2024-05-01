package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quiz", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@PrimaryKeyJoinColumn(name = "quiz_id")
public class Quiz extends Content{

    @Column(name = "quiz_name")
    private String quizName;

    @Column(name = "deadline")
    private java.util.Date deadline;
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    @Column(name = "is_active_now")
    private Boolean isActiveNow;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "quiz")
    private List<QuizQuestion> quizQuestionList;
    @OneToMany(mappedBy = "quiz")
    private List<StudentQuiz> studentQuizList;

}
