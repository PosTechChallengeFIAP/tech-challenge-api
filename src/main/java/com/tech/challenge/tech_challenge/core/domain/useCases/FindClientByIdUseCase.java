package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class FindClientByIdUseCase {

    @Autowired
    private IClientRepository clientRepository;

    public Client execute(UUID id) throws ResourceNotFoundException {
        return clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Client.class, String.format("No Client with ID %s", id))
        );
    }
}
