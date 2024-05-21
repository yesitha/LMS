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
@Table(name = "transaction", schema = "resource_management")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "reference_number")
    private String referenceNumber;
    @Column(name = "note")
    private String note;
    @Column(name = "transaction_date")
    private Date transactionDate;
    @Column(name = "payment_month_for")
    private String paymentMonthFor;
    @Column(name = "payment_year_for")
    private String paymentYearFor;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;
    @OneToMany(mappedBy = "transaction")
    private List<StudentTransactionContent> studentTransactionContentList;


}
