package com.itgura.paymentservice.repository;

import com.itgura.paymentservice.entity.StudentTransactionContent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface StudentTransactionContentRepository extends JpaRepository<StudentTransactionContent, UUID> {

    @Query("SELECT CASE WHEN COUNT(stc) > 0 THEN TRUE ELSE FALSE END FROM StudentTransactionContent stc WHERE stc.studentEmail = :email AND stc.contentId = :contentId")
    Boolean existsByStudentIdAndContentId(String email, UUID contentId);


    @Modifying
    @Query("DELETE FROM StudentTransactionContent stc WHERE stc.contentId = :data")
    void deleteByContentId(UUID data);

    @Query("SELECT stc FROM StudentTransactionContent stc WHERE stc.contentExpireDate < :currentDate")
    List<StudentTransactionContent> findByContentExpireDateBefore(Date currentDate);

    @Query("SELECT stc FROM StudentTransactionContent stc WHERE stc.contentId = :uuid")
    List<StudentTransactionContent> findByContentId(UUID uuid);
}
