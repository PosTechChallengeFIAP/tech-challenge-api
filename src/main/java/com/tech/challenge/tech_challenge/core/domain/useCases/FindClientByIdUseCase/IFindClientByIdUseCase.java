package com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByIdUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public interface IFindClientByIdUseCase {
    Client execute(UUID id);
}
