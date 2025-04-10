package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase.UpdatePaymentUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdatePaymentUseCaseTest {
    @MockBean
    private IPaymentRepository paymentRepository;

    @Autowired
    private UpdatePaymentUseCase updatePaymentUseCase;

    @Test
    public void updateTest_Success() throws Exception {
        Payment payment = mock(Payment.class);

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment paymentResult = updatePaymentUseCase.execute(payment);

        assertEquals(payment,paymentResult);
    }

    @Test
    public void updateTest_Exception() throws Exception {
        Payment payment = mock(Payment.class);

        when(paymentRepository.save(payment)).thenReturn(payment);
        doThrow(ValidationException.class).doNothing().when(payment).validate();

        assertThrows(ValidationException.class, ()->{
            updatePaymentUseCase.execute(payment);
        });
    }
}
