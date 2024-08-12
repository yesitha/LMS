package com.itgura.repository;

import com.itgura.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
}
