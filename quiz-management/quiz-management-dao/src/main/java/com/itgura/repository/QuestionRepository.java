package com.itgura.repository;

import com.itgura.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByQuiz_Id(UUID quizId);
}
