package com.itgura.repository;

import com.itgura.entity.ScheduleSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ScheduleSessionRepositoryRepository extends JpaRepository<ScheduleSession, UUID> {
    @Query("SELECT ss FROM ScheduleSession ss WHERE ss.session.contentId = :sessionId")
    List<ScheduleSession> findBySessionId(@Param("sessionId") UUID sessionId);

}
