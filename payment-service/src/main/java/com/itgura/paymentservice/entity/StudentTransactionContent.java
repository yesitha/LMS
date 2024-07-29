package com.itgura.paymentservice.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    @Column(name = "student_email")
    private String studentEmail;
    @Column(name = "content_id")
    private UUID contentId;
    @Nullable
    @Column(name = "content_expire_date")
    private Date contentExpireDate;
    @ManyToOne
    @JoinColumn(name = "transaction_id",nullable=false)
    private Transaction transaction;
}
