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
public class Transaction {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    private Double amount;
    private String referenceNumber;
    private String note;
    private Date transactionDate;
    private String paymentMonthFor;
    private String paymentYearFor;
    private String studentEmail;
    @OneToMany(mappedBy="transaction")
    private List<StudentTransactionContent> studentTransactionContentList;
}
