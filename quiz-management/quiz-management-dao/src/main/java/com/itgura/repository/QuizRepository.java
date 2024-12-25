package com.itgura.repository;

import com.itgura.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
@EnableJpaRepositories
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @Query("SELECT q FROM Quiz q JOIN q.classIds c WHERE c IN :classIds")
    List<Quiz> findByClassIdsIn(@Param("classIds") List<UUID> classIds);
}
