package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ClientRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
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

    public Client getById(UUID id) throws ResourceNotFoundException {
        return clientRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(Client.class, String.format("No Client with ID %s", id))
        );
    }

    public Client getByCpf(String cpf) throws ResourceNotFoundException, ValidationException {
        String formatedCPf = CPFValidator.formatCPF(cpf); 
        return clientRepository.findByCpf(formatedCPf).orElseThrow(
                () -> new ResourceNotFoundException(Client.class, String.format("No Client with CPF %s", cpf))
        );
    }

    public Client create(Client client) throws ValidationException {

        ValidateClientUseCase.validate(client);

        return clientRepository.save(client);
    }
}
