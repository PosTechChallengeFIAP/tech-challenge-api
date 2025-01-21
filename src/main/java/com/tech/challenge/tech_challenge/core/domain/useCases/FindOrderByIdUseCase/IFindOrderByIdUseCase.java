package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IFindOrderByIdUseCase {
    Order execute(UUID id);
}
