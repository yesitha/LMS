package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quiz_submission", schema = "quiz_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSubmission {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "submitted_at")
    private Timestamp submittedAt;

    @Column(name = "score")
    private Double score;

    @OneToMany(mappedBy = "quizSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswer> answers;


    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Getters and Setters
}

