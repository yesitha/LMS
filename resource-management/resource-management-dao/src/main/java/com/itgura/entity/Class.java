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
@PrimaryKeyJoinColumn(name = "class_id")
public class Class extends Content  {

    @Column(name = "class_name")
    private String className;
    @Column(name = "year")
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "fees_id")
    private Fees fees;
    @OneToMany(mappedBy = "aClass")
    private List<ClassStudent> classStudentList;
    @OneToMany(mappedBy = "aClass")
    private List<Lesson> lessonList;
    @OneToMany(mappedBy = "aClass")
    private List<Schedule> scheduleList;
    @OneToMany(mappedBy = "aClass")
    private List<Announcement> announcementList;
    @OneToMany(mappedBy = "aClass")
    private List<Transaction> transactionList;

}
