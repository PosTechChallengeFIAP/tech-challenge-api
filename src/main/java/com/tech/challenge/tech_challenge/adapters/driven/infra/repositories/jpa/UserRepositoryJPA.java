package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.username =:username")
    Optional<User> findByUsername (@Param("username") String username);
}

