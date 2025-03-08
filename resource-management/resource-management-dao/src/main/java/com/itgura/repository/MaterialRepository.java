package com.itgura.repository;

import com.itgura.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface MaterialRepository  extends JpaRepository<Material, UUID> {
    List<Material> findAllBySession_ContentId(UUID sessionId);
}
