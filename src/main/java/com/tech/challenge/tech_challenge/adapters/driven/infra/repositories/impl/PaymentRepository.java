package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.PaymentRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.PaymentEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.PaymentEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;

@Repository
public class PaymentRepository implements IPaymentRepository {

    @Autowired
    PaymentRepositoryJPA paymentRepositoryJPA;

    @Override
    public Optional<Payment> findById(UUID id) {
        Optional<Payment> payment = paymentRepositoryJPA.findById(id).map(paymentEntity -> toDomain(paymentEntity));
        return payment;
    }

    @Override
    public Payment save(Payment paymentToSave) {
        PaymentEntity paymentEntityToSave = toEntity(paymentToSave);
        PaymentEntity paymentEntity = paymentRepositoryJPA.save(paymentEntityToSave);
        Payment payment = toDomain(paymentEntity);
        return payment;
    }

    private Payment toDomain(PaymentEntity payment) {
        return PaymentEntityMapper.toDomain(payment);
    }

    private PaymentEntity toEntity(Payment payment) {
        return PaymentEntityMapper.toEntity(payment);
    }
}
