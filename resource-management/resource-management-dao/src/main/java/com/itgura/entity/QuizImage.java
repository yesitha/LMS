package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "quiz_image", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@PrimaryKeyJoinColumn(name = "quiz_image_id")
public class QuizImage extends Content{
    @Column(name = "quiz_image")
    private byte[] quizImage;
    @OneToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;
    @OneToOne
    @JoinColumn(name = "answer_id")
    private QuizQuestionAnswer answer;
    @Column(name = "is_question_image")
    private Boolean isQuestionImage;
}
