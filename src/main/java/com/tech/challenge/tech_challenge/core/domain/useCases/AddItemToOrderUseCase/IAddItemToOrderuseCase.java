package com.tech.challenge.tech_challenge.core.domain.useCases.AddItemToOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

public interface IAddItemToOrderuseCase {
    Order execute(UUID orderId, OrderItem orderItem);
}
