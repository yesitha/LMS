package com.itgura.repository;


import com.itgura.entity.User;
import com.itgura.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);
    Optional<User> findByRole(Role role);
}
