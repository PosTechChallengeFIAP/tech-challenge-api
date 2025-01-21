package com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface IFindPaymentByIdUseCase {
    Payment execute(UUID id);
}
