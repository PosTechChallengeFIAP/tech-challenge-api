package com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase;

import com.mercadopago.resources.payment.Payment;

public interface ICraetePaymentUseCase {
    Payment execute(double value);
}
