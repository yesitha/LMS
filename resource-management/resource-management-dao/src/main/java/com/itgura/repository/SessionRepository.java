package com.itgura.repository;

import com.itgura.entity.Lesson;
import com.itgura.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface SessionRepository extends JpaRepository<Session, UUID>{
    ArrayList<Session> findAllByLesson(Lesson lesson);
    @Query(value = "SELECT s.session_id " +
            "FROM session s " +
            "JOIN lesson l ON s.lesson_id = l.lesson_id " +
            "JOIN class c ON l.class_id = c.class_id " +
            "WHERE c.class_id = :classId " +
            "AND EXTRACT(MONTH FROM s.date_and_time) = :month " +
            "AND EXTRACT(YEAR FROM s.date_and_time) = :year",
            nativeQuery = true)
    List<UUID> findAllSessionIdsInMonthAndClass(@Param("classId") UUID classId,
                                                @Param("month") int month,
                                                @Param("year") int year);

}
