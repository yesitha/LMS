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
    private UUID transactionId;
    private Double amount;
    private String note;
    private Date transactionDateTime;
    private int paymentMonthFor;
    private int paymentYearFor;
    private String studentEmail;
    private String orderId;
    @OneToMany(mappedBy="transaction")
    private List<StudentTransactionContent> studentTransactionContentList;
}
