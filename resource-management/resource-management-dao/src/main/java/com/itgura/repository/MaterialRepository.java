package com.itgura.repository;

import com.itgura.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface MaterialRepository  extends JpaRepository<Material, UUID> {
    @Query("SELECT m FROM Material m WHERE m.status = ?1 AND m.createdAt < ?2")
    List<Material> findAllByStatusAndCreatedAtBefore(String pending, Instant cutoffTime);
}
