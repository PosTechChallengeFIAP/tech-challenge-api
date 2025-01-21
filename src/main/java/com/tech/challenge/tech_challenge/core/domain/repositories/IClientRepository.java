package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public interface IClientRepository {
    Client save(Client client);
    Optional<Client> findById(UUID id);
    Optional<Client> findByCpf(String cpf);
    List<Client> findAll();
}
