package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderPaymentUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface IUpdateOrderPaymentUseCase {
    Payment execute(UUID orderId, UUID paymentId, EPaymentStatus status);
}
