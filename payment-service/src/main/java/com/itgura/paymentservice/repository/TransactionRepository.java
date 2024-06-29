package com.itgura.paymentservice.repository;

import com.itgura.paymentservice.entity.Transaction;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
    @Query("SELECT t FROM Transaction t WHERE t.studentEmail = ?1 AND t.classId = ?2 AND t.paymentYearFor = ?3")
    List findByStudentEmailAndClassId(String studentEmail, UUID classId, int value);

    @Query("SELECT t.paymentMonthFor FROM Transaction t WHERE t.studentEmail = ?1 AND t.classId = ?2 AND t.paymentYearFor = ?3 ")
    int[] findMonthsByStudentEmailAndClassId(String studentEmail, UUID classId, int value);

    @Query("SELECT t.transactionId FROM Transaction t WHERE t.paymentMonthFor = ?1 AND t.paymentYearFor = ?2 AND t.classId = ?3")
    java.util.List<UUID> findByMonthAndYearAndClassId(int month, int year, UUID classId);
}
