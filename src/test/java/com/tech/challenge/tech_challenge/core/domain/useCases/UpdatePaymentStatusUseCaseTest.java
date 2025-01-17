package com.tech.challenge.tech_challenge.core.domain.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

@SpringBootTest
public class UpdatePaymentStatusUseCaseTest {
    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @Test
    void updateWithStatusSuccess_success() {
        Payment payment = mock(Payment.class);

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment paymentResult = updatePaymentStatusUseCase.execute(UUID.randomUUID(), UUID.randomUUID(), "success");

        assertEquals(payment,paymentResult);
    }

    @Test
    void updateWithNotSuccessStatus_success() {
        Payment payment = mock(Payment.class);
        UUID randomId = UUID.randomUUID();
        
        payment.setId(randomId);

        when(paymentRepository.findById(randomId)).thenReturn(Optional.of(payment));

        Payment paymentResult = updatePaymentStatusUseCase.execute(UUID.randomUUID(), randomId, "not_success");

        assertEquals(payment,paymentResult);
    }
}
