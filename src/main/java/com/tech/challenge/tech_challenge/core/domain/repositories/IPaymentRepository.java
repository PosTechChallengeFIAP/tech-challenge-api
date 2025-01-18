package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public interface IPaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
}
