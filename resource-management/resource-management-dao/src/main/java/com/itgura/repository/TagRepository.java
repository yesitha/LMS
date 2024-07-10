package com.itgura.repository;

import com.itgura.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@EnableJpaRepositories
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    @Query(value = "SELECT EXISTS(SELECT 1 FROM tag WHERE tagName = :tag)", nativeQuery = true)
    boolean existByName(String tag);


    @Query(value = "SELECT * FROM tag WHERE tagName = :tag", nativeQuery = true)
    Tag findByTagName(String tag);
}
