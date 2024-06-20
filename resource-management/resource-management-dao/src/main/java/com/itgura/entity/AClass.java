package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "class", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "class_id")
public class AClass extends Content  {

    @Column(name = "class_name")
    private String className;
    @Column(name = "year")
    private Integer year;
    @Column(name = "fees")
    private Double fees;
    @OneToMany(mappedBy = "aClass",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ClassStudent> classStudentList;
    @OneToMany(mappedBy = "aClass",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Lesson> lessonList;
    @OneToOne(mappedBy = "aClass", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Schedule schedule; // Unique relationship with Schedule
    @OneToMany(mappedBy = "aClass",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Announcement> announcementList;


}
