package com.tech.challenge.tech_challenge.core.domain.useCases.FindClientsUseCase;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public interface IFindClientsUseCase {
    List<Client> execute();
}
