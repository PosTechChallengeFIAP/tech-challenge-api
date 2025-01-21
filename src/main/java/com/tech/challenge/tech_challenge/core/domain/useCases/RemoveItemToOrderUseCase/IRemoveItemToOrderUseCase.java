package com.tech.challenge.tech_challenge.core.domain.useCases.RemoveItemToOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IRemoveItemToOrderUseCase {
    Order execute(UUID orderId, UUID itemId);
}
