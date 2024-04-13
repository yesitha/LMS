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
public class QuizImage {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quiz_image_id")
    private UUID quizImageId;
    @Column(name = "image")
    private byte[] image;
    @OneToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;
    @OneToOne
    @JoinColumn(name = "answer_id")
    private QuizQuestionAnswer answer;
    @Column(name = "is_question_image")
    private Boolean isQuestionImage;
}
