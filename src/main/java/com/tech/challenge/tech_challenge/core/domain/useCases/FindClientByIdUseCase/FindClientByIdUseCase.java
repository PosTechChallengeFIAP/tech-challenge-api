package com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class FindClientByIdUseCase implements IFindClientByIdUseCase {

    @Autowired
    private IClientRepository clientRepository;

    public Client execute(UUID id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Client.class, String.format("No Client with ID %s", id))
        );
    }
}
