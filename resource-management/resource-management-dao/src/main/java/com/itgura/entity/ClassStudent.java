package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "class_student", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassStudent {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_student_id")
    private UUID classStudentId;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private AClass aClass;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
