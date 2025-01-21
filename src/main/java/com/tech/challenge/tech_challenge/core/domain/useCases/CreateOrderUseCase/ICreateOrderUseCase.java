package com.tech.challenge.tech_challenge.core.domain.useCases.CreateOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface ICreateOrderUseCase {
    Order execute(Order order);
}
