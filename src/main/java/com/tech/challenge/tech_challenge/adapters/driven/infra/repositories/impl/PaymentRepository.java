package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.PaymentRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;

@Repository
public class PaymentRepository implements IPaymentRepository {

    @Autowired
    PaymentRepositoryJPA paymentRepositoryJPA;

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentRepositoryJPA.findById(id);
    }

    @Override
    public Payment save(Payment paymentToSave) {
        return paymentRepositoryJPA.save(paymentToSave);
    }
}
