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
public class Material  {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "material_id")
    private UUID materialId;
    @Column(name = "material_name")
    private String materialName;
    @Column(name = "reference")
    private String reference;
    @Column(name = "uploaded_by")
    @Lob
    private UUID uploadedBy;
    @Column(name = "uploaded_on")
    private java.util.Date uploadedOn;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

}
