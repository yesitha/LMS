package com.itgura.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transaction", schema = "payment_service")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//If user pay for multiple months,then we save multiple transaction for each month with same order id
public class Transaction {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;
    private Double amount;
    private String note;
    @Column(name = "transaction_date_time")
    private Date transactionDateTime;
    @Column(name = "payment_month_for")
    private int paymentMonthFor;
    @Column(name = "payment_year_for")
    private int paymentYearFor;
    @Column(name = "student_email")
    private String studentEmail;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "class_id")
    private UUID classId;
    @OneToMany(mappedBy="transaction",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StudentTransactionContent> studentTransactionContentList;
}
