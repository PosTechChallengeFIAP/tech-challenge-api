package com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentStatusUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface IUpdatePaymentStatusUseCase {
    Payment execute(UUID orderId, UUID paymentId, String paymentStatus);
}
