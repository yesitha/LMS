package com.itgura.repository;

import com.itgura.entity.AClass;
import com.itgura.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
        @Query("SELECT a FROM Announcement a WHERE a.aClass.contentId = :classId")
        List<Announcement> findAllByAClass(UUID classId);
}
