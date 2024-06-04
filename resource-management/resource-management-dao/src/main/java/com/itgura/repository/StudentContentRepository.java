package com.itgura.repository;


import com.itgura.entity.Student;
import com.itgura.entity.StudentTransactionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface StudentContentRepository extends JpaRepository<StudentTransactionContent, UUID> {
    List<StudentTransactionContent> findStudentTransactionContentsByStudent(Student student);
}
