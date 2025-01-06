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
@Table(name = "assignment_submission", schema = "quiz_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentSubmission {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "submitted_at")
    private Timestamp submittedAt;

    @Column(name = "file_url")
    private String fileUrl; // URL to the student's uploaded file

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
