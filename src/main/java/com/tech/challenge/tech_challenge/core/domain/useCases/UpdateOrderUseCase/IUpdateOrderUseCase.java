package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IUpdateOrderUseCase {
    Order execute(UUID id, Order order) throws IllegalAccessException;
    Order execute(Order order);
}
