package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createNewPayment(double value) throws ValidationException {
        Payment payment = new Payment();
        payment.setSatus(EPaymentStatus.PENDING);
        payment.setValue(value);
        ValidatePayment.validate(payment.getValue());
        return paymentRepository.save(payment);
    }

    public Payment update(Payment payment) throws ValidationException {
        ValidatePayment.validate(payment.getValue());

        return paymentRepository.save(payment);
    }

    public Payment getById(UUID id) throws ResourceNotFoundException {
        return paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Payment.class, String.format("No payment with ID %s.", id))
        );
    }
}
