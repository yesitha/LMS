package com.itgura.repository;
import com.itgura.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
@EnableJpaRepositories
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, UUID> {
    List<AssignmentSubmission> findByAssignmentId(UUID assignmentId);
}