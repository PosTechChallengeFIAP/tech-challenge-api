package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.PaymentEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

public class PaymentEntityMapper {
    public static Payment toDomain(PaymentEntity paymentEntity) {
        Payment payment = new Payment();
        payment.setId(paymentEntity.getId());
        payment.setValue(paymentEntity.getValue());
        payment.setStatus(paymentEntity.getStatus());
        payment.setPaymentURL(paymentEntity.getPaymentURL());
        return payment;
    }

    public static PaymentEntity toEntity(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId());
        paymentEntity.setValue(payment.getValue());
        paymentEntity.setStatus(payment.getStatus());
        paymentEntity.setPaymentURL(payment.getPaymentURL());
        return paymentEntity;
    }
}
