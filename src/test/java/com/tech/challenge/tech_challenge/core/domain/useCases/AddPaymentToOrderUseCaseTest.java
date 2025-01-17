package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddPaymentToOrderUseCaseTest {
    @MockBean
    private OrderRepository orderRepository;

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
        payment.setSatus(EPaymentStatus.PENDING);
        payment.setValue(value);

        return payment;
    }
}
