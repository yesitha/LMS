package com.itgura.repository;

import com.itgura.entity.AClass;
import com.itgura.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface FeesRepository  extends JpaRepository<Fees, UUID> {
}
