package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase.FindPaymentByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase.ReceiveOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderPaymentUseCase.UpdateOrderPaymentUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateOrderPaymentUseCaseTest {

    @MockBean
    private IOrderRepository orderRepository;

    @MockBean
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @MockBean
    private UpdatePaymentUseCase updatePaymentUseCase;

    @MockBean
    private ReceiveOrderUseCase receiveOrderUseCase;

    @Autowired
    private UpdateOrderPaymentUseCase updateOrderPaymentUseCase;
    @Test
    public void updateOrderPaymentTest() throws Exception {
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(findPaymentByIdUseCase.execute(order.getPayment().getId())).thenReturn(order.getPayment());
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        when(updatePaymentUseCase.execute(order.getPayment())).thenReturn(order.getPayment());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(receiveOrderUseCase.execute(order)).thenReturn(mock(Queue.class));

        Payment paymentResult = updateOrderPaymentUseCase.execute(order.getId(), order.getPayment().getId(), EPaymentStatus.PAID);

        assertEquals(paymentResult, order.getPayment());
        assertEquals(paymentResult.getStatus(), EPaymentStatus.PAID);
        assertEquals(paymentResult.getValue(), order.getPrice());
    }
}
