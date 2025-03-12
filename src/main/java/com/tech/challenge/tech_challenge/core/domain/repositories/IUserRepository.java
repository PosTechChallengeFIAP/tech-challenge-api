package com.tech.challenge.tech_challenge.core.domain.repositories;

import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
}
