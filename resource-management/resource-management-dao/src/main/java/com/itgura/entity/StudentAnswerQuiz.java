package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "student_answer_quiz", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentAnswerQuiz {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_answer_quiz_id")
    private UUID studentAnswerQuizId;
    @ManyToOne
    @JoinColumn(name = "student_quiz_id")
    private StudentQuiz studentQuiz;
    @ManyToOne
    @JoinColumn(name = "Quiz_question_id")
    private QuizQuestion quizQuestion;
    @ManyToOne
    @JoinColumn(name = "student_answer_id")
    private QuizQuestionAnswer studentAnswer;
    @ManyToOne
    @JoinColumn(name = "correct_answer_id")
    private QuizQuestionAnswer correctAnswer;
    @Column(name = "is_correct")
    private Boolean isCorrect;
    @Column(name = "obtained_marks")
    private Double marks;


}
