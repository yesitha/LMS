package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "paper", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@PrimaryKeyJoinColumn(name = "paper_id")
public class Paper extends Content {


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
    private AClass aClass;
    @ManyToOne
    @JoinColumn(name = "paper_type_id")
    private PaperType paperType;

}
