package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ClientEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public class ClientEntityMapper {
    public static Client toDomain(ClientEntity clientEntity) {
        Client client = new Client();
        client.setId(clientEntity.getId());
        client.setName(clientEntity.getName());
        client.setEmail(clientEntity.getEmail());
        return client;
    }

    public static ClientEntity toEntity(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client.getId());
        clientEntity.setName(client.getName());
        clientEntity.setEmail(client.getEmail());
        return clientEntity;
    }
}
