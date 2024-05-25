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


}
