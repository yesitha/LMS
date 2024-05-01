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
@PrimaryKeyJoinColumn(name = "quiz_question_answer_id")
public class QuizQuestionAnswer extends Content{

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;
    @OneToMany(mappedBy = "studentAnswer")
    private List<StudentAnswerQuiz> studentAnswerQuizList;
    @OneToMany(mappedBy = "correctAnswer")
    private List<StudentAnswerQuiz> correctAnswerQuizList;
}
