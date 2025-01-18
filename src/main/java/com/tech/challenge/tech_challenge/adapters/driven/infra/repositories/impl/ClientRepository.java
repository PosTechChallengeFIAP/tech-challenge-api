package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.ClientRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepository implements IClientRepository {

    @Autowired
    private ClientRepositoryJPA clientRepositoryJPA;


    @Override
    public Client save(Client clientToSave) {
        return clientRepositoryJPA.save(clientToSave);
    }

    @Override
    public List<Client> findAll() {
        return clientRepositoryJPA.findAll();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return clientRepositoryJPA.findById(id);
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return clientRepositoryJPA.findByCpf(cpf);
    }
}
