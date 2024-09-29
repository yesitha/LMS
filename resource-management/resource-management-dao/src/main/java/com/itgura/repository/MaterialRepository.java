package com.itgura.repository;

import com.itgura.entity.Material;
import com.itgura.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface MaterialRepository  extends JpaRepository<Material, UUID> {

    @Query(value = "SELECT * FROM material WHERE session_id = :session.id AND ", nativeQuery = true)
    List<Material> findAllVideoMaterialsBySession(Session session);
}
