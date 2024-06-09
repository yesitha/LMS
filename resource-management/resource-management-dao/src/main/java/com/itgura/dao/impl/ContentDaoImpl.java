package com.itgura.dao.impl;

import com.itgura.dao.ContentDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ContentDaoImpl implements ContentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UUID> getUserContentIds(UUID userId, UUID lessonId) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");
        query.append("stc.content_id ");
        query.append("FROM ");
        query.append("resource_management.student_transaction_content as stc ");
        query.append("WHERE ");
        query.append("content_id in ");
        query.append("(SELECT ");
        query.append("s.session_id ");
        query.append("FROM ");
        query.append("resource_management.session as s ");
        query.append("WHERE ");
        query.append("s.lesson_id = :lessonId) ");
        query.append("AND ");
        query.append("stc.student_id = :userId");

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("lessonId", lessonId);
        return nativeQuery.getResultList();

    }
}
