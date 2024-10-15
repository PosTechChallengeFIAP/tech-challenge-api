package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ClientRepository;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> list(){
        return clientRepository.findAll();
    }

    public Client getById(UUID id) throws Exception {
        return clientRepository.findById(id).orElseThrow(
            () -> new Exception("Unable to Find Client")
        );
    }

    public Client create(Client client) {
        Error error = client.validate();
        if(!Objects.isNull(error)){
            throw error;
        }

        return clientRepository.save(client);
    }
}
