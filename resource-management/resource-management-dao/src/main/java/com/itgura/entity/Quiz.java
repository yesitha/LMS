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
public class Quiz {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quiz_id")
    private UUID quizId;
    @Column(name = "quiz_name")
    private String quizName;
    @Column(name = "created_date")
    private java.util.Date createdDate;
    @Column(name = "created_by")
    @Lob
    private UUID createdBy;
    @Column(name = "deadline")
    private java.util.Date deadline;
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    @Column(name = "is_active_now")
    private Boolean isActiveNow;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @OneToMany(mappedBy = "quiz",fetch = FetchType.EAGER)
    private List<QuizQuestion> quizQuestionList;
    @OneToMany(mappedBy = "quiz",fetch = FetchType.EAGER)
    private List<StudentQuiz> studentQuizList;

}
