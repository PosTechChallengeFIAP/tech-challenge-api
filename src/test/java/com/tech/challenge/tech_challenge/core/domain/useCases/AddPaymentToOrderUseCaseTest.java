package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddPaymentToOrderUseCase.AddPaymentToOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase.CreatePaymentUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddPaymentToOrderUseCaseTest {
    @MockBean
    private IOrderRepository orderRepository;

    @MockBean
    private CreatePaymentUseCase createPaymentUseCase;

    @Autowired
    private AddPaymentToOrderUseCase  addPaymentToOrderUseCase;

    @Test
    public void addPaymentToOrderTest() throws Exception {
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.withPayment(null).build();
        Payment payment = buildNewPayment(order.getPrice());

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        when(createPaymentUseCase.execute(order.getPrice())).thenReturn(payment);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order orderResult = addPaymentToOrderUseCase.execute(order.getId());

        assertEquals(orderResult, order);
        assertEquals(orderResult.getStatus(), EOrderStatus.PAYMENT_PENDING);
        assertEquals(orderResult.getPayment(),payment);
        assertEquals(orderResult.getPrice(), orderResult.getPayment().getValue());
    }
    private Payment buildNewPayment(double value){
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setStatus(EPaymentStatus.PENDING);
        payment.setValue(value);

        return payment;
    }
}
