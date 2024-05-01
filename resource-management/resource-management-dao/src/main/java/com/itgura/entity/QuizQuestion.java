package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quiz_question", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@PrimaryKeyJoinColumn(name = "quiz_question_id")
public class QuizQuestion extends Content {

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;
    @Column(name = "marks")
    private Double marks;
    @Column(name = "is_mcq")
    private Boolean isMcq;
    @OneToOne
    @JoinColumn(name = "correct_answer_id")
    private QuizQuestionAnswer correctAnswer;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @OneToMany(mappedBy = "quizQuestion")
    private List<StudentAnswerQuiz> studentAnswerQuizList;

}
