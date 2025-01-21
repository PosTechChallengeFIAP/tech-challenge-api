package com.tech.challenge.tech_challenge.core.domain.useCases.AddPaymentToOrderUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IAddPaymentToOrderUseCase {
    Order execute(UUID orderId);
}
