package com.tech.challenge.tech_challenge.core.domain.useCases.FindClientByCpfUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;

public interface IFindClientByCpfUseCase {
    Client execute(String cpf);
}
