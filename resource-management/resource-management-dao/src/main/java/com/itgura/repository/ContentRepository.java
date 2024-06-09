package com.itgura.repository;

import com.itgura.entity.Content;
import com.itgura.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
    @Query(value = "SELECT resource_management.check_student_content_match(:sessionId, :contentId)", nativeQuery = true)
    Boolean checkStudentAvailableContent(@Param("sessionId") UUID sessionId, @Param("contentId") UUID contentId);

}
