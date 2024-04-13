package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "paper_type", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaperType {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "paper_type_id")
    private UUID paperTypeId;
    @Column(name = "paper_type_name")
    private String paperTypeName;
}
