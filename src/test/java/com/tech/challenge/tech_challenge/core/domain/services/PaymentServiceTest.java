package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentRepository paymentRepository;

    @Test
    public void createNewPaymentTest() throws Exception {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment payment = paymentService.createNewPayment(30.0);

        assertEquals(payment.getStatus(), EPaymentStatus.PENDING);
        assertEquals(payment.getValue(), 30.0);
    }

    @Test
    public void getByIdTest_Success() throws Exception {
        Payment payment = new Payment();

        payment.setId(UUID.randomUUID());

        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        Payment orderResult = paymentService.getById(payment.getId());

        assertEquals(payment, orderResult);
    }

    @Test
    public void getByIdTest_Exception(){
        UUID id = UUID.randomUUID();

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, ()->{
            paymentService.getById(id);
        });

        assertEquals(ex.getMessage(), "Unable to find payment");
    }

    @Test
    public void updateTest_Success() throws Exception {
        Payment payment = mock(Payment.class);

        when(paymentRepository.save(payment)).thenReturn(payment);
        when(payment.validate()).thenReturn(null);

        Payment paymentResult = paymentService.update(payment);

        assertEquals(payment,paymentResult);
    }

    @Test
    public void updateTest_Exception() throws Exception {
        Payment payment = mock(Payment.class);
        Error error = mock(Error.class);

        when(paymentRepository.save(payment)).thenReturn(payment);
        when(payment.validate()).thenReturn(error);

        Error thrownError = assertThrows(Error.class, ()->{
            paymentService.update(payment);
        });

        assertEquals(thrownError,error);
    }
}
