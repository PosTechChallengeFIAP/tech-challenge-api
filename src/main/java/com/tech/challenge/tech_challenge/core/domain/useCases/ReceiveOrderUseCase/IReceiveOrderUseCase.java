package com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

public interface IReceiveOrderUseCase {
    Queue execute(Order order);
}
