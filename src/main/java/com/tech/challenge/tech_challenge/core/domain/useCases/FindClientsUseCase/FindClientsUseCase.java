package com.tech.challenge.tech_challenge.core.domain.useCases.FindClientsUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindClientsUseCase implements IFindClientsUseCase {

    @Autowired
    private IClientRepository clientRepository;

    public List<Client> execute(){
        return clientRepository.findAll();
    }
}
