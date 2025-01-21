package com.tech.challenge.tech_challenge.core.domain.useCases.EditItemToOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

public interface IEditItemToOrderUseCase {
    Order execute(UUID orderId, UUID itemId, OrderItem newOrderItem);
}
