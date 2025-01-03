package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "material", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "material_id")
public class Material extends Content  {

    @Column(name = "material_name")
    private String materialName;
    @Column(name = "reference")
    private String reference;
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

}
