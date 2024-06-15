package com.itgura.paymentservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "student_content", schema = "payment_service")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentTransactionContent {
    @Id
    @Lob
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_transaction_content_id")
    private UUID studentTransactionContentId;
    private String studentEmail;
    private UUID contentId;
    @ManyToOne
    @JoinColumn(name = "transaction_id",nullable=false)
    private Transaction transaction;
}
