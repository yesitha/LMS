package com.itgura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payment", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "payment_month_for")
    private String paymentMonthFor;
    @Column(name = "payment_year_for")
    private String paymentYearFor;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;
    @OneToMany(mappedBy = "payment",fetch = FetchType.EAGER)
    private List<StudentSession> studentSessionList;

}
