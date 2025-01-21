package com.tech.challenge.tech_challenge.core.domain.useCases.CreateClientUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {

    @Autowired
    private IClientRepository clientRepository;
    
    public Client execute(Client client) {
        client.validate();
        return clientRepository.save(client);
    }
}
