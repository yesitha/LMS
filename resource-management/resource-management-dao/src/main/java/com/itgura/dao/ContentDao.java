package com.itgura.dao;

import java.util.List;
import java.util.UUID;

public interface ContentDao {
    List<UUID> getUserContentIds(UUID userId, UUID lessonId);
}
