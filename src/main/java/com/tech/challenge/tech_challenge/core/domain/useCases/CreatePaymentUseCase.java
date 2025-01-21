package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePaymentUseCase {
    @Autowired
    private IPaymentRepository paymentRepository;

    public Payment execute(double value) throws ValidationException {
        Payment payment = new Payment();
        payment.setStatus(EPaymentStatus.PENDING);
        payment.setValue(value);
        payment.validate();
        return paymentRepository.save(payment);
    }
}
