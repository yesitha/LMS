package com.itgura.repository;

import com.itgura.entity.AClass;
import com.itgura.entity.ContentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface ContentTagRepository extends JpaRepository<ContentTag, UUID>  {
    @Query(value = "SELECT * FROM content_tag WHERE content_id = :contentId", nativeQuery = true)
    List<ContentTag> findByContentTagId(UUID contentId);
}
