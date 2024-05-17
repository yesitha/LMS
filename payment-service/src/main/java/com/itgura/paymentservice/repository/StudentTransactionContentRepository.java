package com.itgura.paymentservice.repository;

import com.itgura.paymentservice.entity.StudentTransactionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface StudentTransactionContentRepository extends JpaRepository<StudentTransactionContent, UUID> {

    @Query("SELECT CASE WHEN COUNT(stc) > 0 THEN TRUE ELSE FALSE END FROM StudentTransactionContent stc WHERE stc.studentEmail = :email AND stc.contentId = :contentId")
    Boolean existsByStudentIdAndContentId(String email, UUID contentId);
}
