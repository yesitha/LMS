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
@Table(name = "assignment", schema = "quiz_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Assignment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "assignment_files", joinColumns = @JoinColumn(name = "assignment_id"))
    @Column(name = "file_url")
    private List<UUID> fileUrls; // List of URLs to files that students need to download

    @Column(name = "deadline")
    private Timestamp deadline;
    @Column(name = "duration")
    private Long duration; // Duration in minutes

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ElementCollection
    @CollectionTable(name = "assignment_class", joinColumns = @JoinColumn(name = "assignment_id"))
    @Column(name = "class_id")
    private List<UUID> classIds;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentSubmission> submissions;

    // Getters and Setters
}
