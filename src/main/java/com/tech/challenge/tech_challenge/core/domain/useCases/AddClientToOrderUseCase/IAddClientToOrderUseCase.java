package com.tech.challenge.tech_challenge.core.domain.useCases.AddClientToOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IAddClientToOrderUseCase {
    Order execute(UUID orderId, UUID clientId);
}
