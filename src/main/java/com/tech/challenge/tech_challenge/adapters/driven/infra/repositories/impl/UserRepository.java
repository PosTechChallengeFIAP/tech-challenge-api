package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.ClientRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.UserRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.auth.User;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;
import com.tech.challenge.tech_challenge.core.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    @Override
    public User save(User userToSave) {
        return userRepositoryJPA.save(userToSave);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepositoryJPA.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username);
    }
}
