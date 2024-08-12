package com.itgura.repository;

import com.itgura.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    @Query("SELECT a FROM Assignment a JOIN a.classIds c WHERE c IN :classIds")
    List<Assignment> findByClassIdsIn(@Param("classIds") List<UUID> classIds);
}
