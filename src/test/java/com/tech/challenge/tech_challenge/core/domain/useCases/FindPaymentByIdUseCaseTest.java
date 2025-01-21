package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase.FindPaymentByIdUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindPaymentByIdUseCaseTest {
    @MockBean
    private IPaymentRepository paymentRepository;

    @Autowired
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @Test
    public void getByIdTest_Success() throws Exception {
        Payment payment = new Payment();

        payment.setId(UUID.randomUUID());

        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        Payment orderResult = findPaymentByIdUseCase.execute(payment.getId());

        assertEquals(payment, orderResult);
    }

    @Test
    public void getByIdTest_Exception(){
        UUID id = UUID.randomUUID();

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            findPaymentByIdUseCase.execute(id);
        });
    }
}
