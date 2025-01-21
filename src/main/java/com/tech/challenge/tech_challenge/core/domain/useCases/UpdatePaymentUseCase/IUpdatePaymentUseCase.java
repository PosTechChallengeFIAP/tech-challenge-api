package com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface IUpdatePaymentUseCase {
    Payment execute(Payment payment);
}
