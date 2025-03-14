package com.itgura.repository;


import com.itgura.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByUserId(UUID userId);


    @Query("SELECT MAX(s.registration_number) FROM Student s")
    Integer findMaxRegistrationNumber();

    @Modifying
    @Query("UPDATE Student s SET s.profilePicture = :profilePicture WHERE s.userId = :userId")
    int updateProfilePicture( UUID userId,Byte[] profilePicture);

    @Modifying
    @Query("UPDATE Student s SET s.profilePictureName = :originalFilename WHERE s.userId = :userId")
    void updateProfilePictureName(UUID userId, String originalFilename);
}
