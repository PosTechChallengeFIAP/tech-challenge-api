package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createNewPayment(double value) throws Exception {
        Payment payment = new Payment();
        payment.setSatus(EPaymentStatus.PENDING);
        payment.setValue(value);
        this.validatePayment(payment);
        return paymentRepository.save(payment);
    }

    public Payment update(Payment payment) throws Exception {
        validatePayment(payment);

        return paymentRepository.save(payment);
    }

    public Payment getById(UUID id) throws Exception {
        return paymentRepository.findById(id).orElseThrow(
                () -> new Exception("Unable to find payment")
        );
    }

    private void validatePayment(Payment payment) throws Exception {
        Error err = payment.validate();
        if(err != null) {
            throw err;
        }
    }
}
