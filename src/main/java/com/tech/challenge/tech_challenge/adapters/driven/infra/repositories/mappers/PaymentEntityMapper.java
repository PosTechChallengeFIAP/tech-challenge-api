package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.PaymentEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public class PaymentEntityMapper {
    public static Payment toDomain(PaymentEntity paymentEntity) {
        Payment payment = new Payment();
        return payment;
    }

    public static PaymentEntity toEntity(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();
        return paymentEntity;
    }
}
