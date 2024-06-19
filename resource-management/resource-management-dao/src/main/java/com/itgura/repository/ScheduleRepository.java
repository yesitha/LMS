package com.itgura.repository;

import com.itgura.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Schedule findByAClass(UUID scheduleId);
}
