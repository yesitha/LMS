package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "class", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Class {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_id")
    private UUID classId;
    @Column(name = "class_name")
    private String className;
    @Column(name = "year")
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "fees_id")
    private Fees fees;
    @OneToMany(mappedBy = "aClass",fetch = FetchType.EAGER)
    private List<ClassStudent> classStudentList;
    @OneToMany(mappedBy = "aClass",fetch = FetchType.EAGER)
    private List<Lesson> lessonList;
    @OneToMany(mappedBy = "aClass",fetch = FetchType.EAGER)
    private List<Schedule> scheduleList;
    @OneToMany(mappedBy = "aClass",fetch = FetchType.EAGER)
    private List<Announcement> announcementList;
    @OneToMany(mappedBy = "aClass",fetch = FetchType.EAGER)
    private List<Payment> paymentList;

}
