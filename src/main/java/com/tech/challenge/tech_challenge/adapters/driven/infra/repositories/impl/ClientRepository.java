package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.ClientRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.ClientEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ClientEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepository implements IClientRepository {

    @Autowired
    private ClientRepositoryJPA clientRepositoryJPA;


    @Override
    public Client save(Client clientToSave) {
        ClientEntity clientEntityToSave = toEntity(clientToSave);
        ClientEntity clientEntity = clientRepositoryJPA.save(clientEntityToSave);
        Client client = toDomain(clientEntity);
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> clientsEntity = clientRepositoryJPA.findAll();
        List<Client> clients = toDomain(clientsEntity);
        return clients;
    }

    @Override
    public Optional<Client> findById(UUID id) {
        Optional<Client> client = clientRepositoryJPA.findById(id).map(userEntity -> toDomain(userEntity));
        return client;
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        Optional<Client> client= clientRepositoryJPA.findByCpf(cpf).map(userEntity -> toDomain(userEntity));
        return client;
    }

    private Client toDomain(ClientEntity clientEntity) {
        Client client = ClientEntityMapper.toDomain(clientEntity);
        return client;
    }

    private List<Client> toDomain(List<ClientEntity> clientsEntity) {
        List<Client> clients = new ArrayList<>();

        clientsEntity.forEach(clientEntity -> {
            Client transformedClient = ClientEntityMapper.toDomain(clientEntity);
            clients.add(transformedClient);
        });

        return clients;
    }

    private ClientEntity toEntity(Client client) {
        ClientEntity clientEntity = ClientEntityMapper.toEntity(client);
        return clientEntity;
    }
}
