package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quiz_question_answer", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuizQuestionAnswer {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quiz_question_answer_id")
    private UUID quizQuestionAnswerId;
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;
    @OneToMany(mappedBy = "studentAnswer",fetch = FetchType.EAGER)
    private List<StudentAnswerQuiz> studentAnswerQuizList;
    @OneToMany(mappedBy = "correctAnswer",fetch = FetchType.EAGER)
    private List<StudentAnswerQuiz> correctAnswerQuizList;
}
