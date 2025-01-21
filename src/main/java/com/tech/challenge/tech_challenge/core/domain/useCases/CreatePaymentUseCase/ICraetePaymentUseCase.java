package com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface ICraetePaymentUseCase {
    Payment execute(double value);
}
