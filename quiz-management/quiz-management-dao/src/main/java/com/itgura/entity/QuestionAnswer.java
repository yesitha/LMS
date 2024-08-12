package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "question_answer", schema = "quiz_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionAnswer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_submission_id", nullable = false)
    private QuizSubmission quizSubmission; // Added reference to QuizSubmission

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "file_url")
    private UUID fileUrl; // URL to the file or image with the answer

    @Column(name = "submitted_at")
    private Timestamp submittedAt;

    // Getters and Setters
}
