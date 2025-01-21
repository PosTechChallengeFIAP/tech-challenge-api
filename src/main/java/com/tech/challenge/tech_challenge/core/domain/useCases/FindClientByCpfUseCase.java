package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindClientByCpfUseCase {

    @Autowired
    private IClientRepository clientRepository;

    public Client execute(String cpf) throws ResourceNotFoundException, ValidationException {
        String formatedCPf = CPFValidator.formatCPF(cpf);
        return clientRepository.findByCpf(formatedCPf).orElseThrow(
                () -> new ResourceNotFoundException(Client.class, String.format("No Client with CPF %s", cpf))
        );
    }
}
