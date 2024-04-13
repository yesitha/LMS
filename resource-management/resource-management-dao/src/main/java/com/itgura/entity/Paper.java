package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "paper", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Paper {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "paper_id")
    private UUID paperId;
    @Column(name = "paper_name")
    private String paperName;
    @Column(name = "year")
    private Integer year;
    @Column(name = "reference")
    private String reference;
    @Column(name = "uploaded_date")
    private Date uploadedDate;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;
    @ManyToOne
    @JoinColumn(name = "paper_type_id")
    private PaperType paperType;

}
