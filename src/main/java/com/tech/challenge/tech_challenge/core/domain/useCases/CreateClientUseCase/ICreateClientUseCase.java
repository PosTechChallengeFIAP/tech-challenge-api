package com.tech.challenge.tech_challenge.core.domain.useCases.CreateClientUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public interface ICreateClientUseCase {
    Client execute(Client client);
}
