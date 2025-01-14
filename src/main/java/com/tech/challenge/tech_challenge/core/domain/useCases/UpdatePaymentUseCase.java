package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePaymentUseCase {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment execute(Payment payment) throws ValidationException {
        payment.validate();

        return paymentRepository.save(payment);
    }

}
