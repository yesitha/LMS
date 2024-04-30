package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id")
    private UUID studentId;
    @Column(name = "registration_number")
    private Integer registration_number;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "examination_year")
    private Integer examinYear;
    @Column(name = "gender")
    private String gender;
    @Column(name = "school")
    private String school;
    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private List<ClassStudent> classStudentList;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private List<Transaction> transactionList;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private List<StudentTransactionContent> studentTransactionContentList;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private List<StudentQuiz> studentQuizList;


}
