package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase.CreatePaymentUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreatePaymentUseCaseTest {
    @Autowired
    private CreatePaymentUseCase createPaymentUseCase;
    @MockBean
    private IPaymentRepository paymentRepository;
    @Test
    public void createNewPaymentTest() throws Exception {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment payment = createPaymentUseCase.execute(30.0);

        assertEquals(payment.getStatus(), EPaymentStatus.PENDING);
        assertEquals(payment.getValue(), 30.0);
    }
}
