package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "material", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "material_id") // For video this was video_id
public class Material extends Content  {

    @Column(name = "material_name") // For video this was original video name
    private String materialName;
    @Column(name = "reference")  // For video this was S3 Key
    private String reference;
    @Column(name="signedUrlExpireTime")
    private int signedUrlExpireTime;
    @Column(name="status")
    private String status; // For video this was video status (pending,uploaded)
    @Column(name="created_at")
    private Instant createdAt; // For video this was video created at
    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "material_type_id")
    private MaterialType materialType;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    @Column(name = "is_available_for_students")
    private Boolean isAvailableForStudents;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

}
