package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindPaymentByIdUseCase {

    @Autowired
    private IPaymentRepository paymentRepository;

    public Payment execute(UUID id) throws ResourceNotFoundException {
        return paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Payment.class, String.format("No payment with ID %s.", id))
        );
    }
}
