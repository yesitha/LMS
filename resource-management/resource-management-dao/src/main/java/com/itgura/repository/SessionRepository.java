package com.itgura.repository;

import com.itgura.entity.Lesson;
import com.itgura.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface SessionRepository extends JpaRepository<Session, UUID>{
    ArrayList<Session> findAllByLesson(Lesson lesson);

}