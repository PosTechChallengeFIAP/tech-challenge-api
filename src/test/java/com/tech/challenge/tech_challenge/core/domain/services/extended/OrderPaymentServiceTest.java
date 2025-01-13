package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.services.QueueService;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase;
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
public class OrderPaymentServiceTest {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CreatePaymentUseCase createPaymentUseCase;

    @MockBean
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @MockBean
    private UpdatePaymentUseCase updatePaymentUseCase;

    @MockBean
    private QueueService queueService;

    @Test
    public void addClientToOrderTest() throws Exception {
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.withPayment(null).build();
        Payment payment = buildNewPayment(order.getPrice());

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        when(createPaymentUseCase.execute(order.getPrice())).thenReturn(payment);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order orderResult = orderPaymentService.addPaymentToOrder(order.getId());

        assertEquals(orderResult, order);
        assertEquals(orderResult.getStatus(), EOrderStatus.PAYMENT_PENDING);
        assertEquals(orderResult.getPayment(),payment);
        assertEquals(orderResult.getPrice(), orderResult.getPayment().getValue());
    }

    @Test
    public void updateOrderPaymentTest() throws Exception {
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.build();

        when(findPaymentByIdUseCase.execute(order.getPayment().getId())).thenReturn(order.getPayment());
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        when(updatePaymentUseCase.execute(order.getPayment())).thenReturn(order.getPayment());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(queueService.receiveOrder(order)).thenReturn(mock(Queue.class));

        Payment paymentResult = orderPaymentService.updateOrderPayment(order.getId(), order.getPayment().getId(), EPaymentStatus.PAID);

        assertEquals(paymentResult, order.getPayment());
        assertEquals(paymentResult.getStatus(), EPaymentStatus.PAID);
        assertEquals(paymentResult.getValue(), order.getPrice());
    }

    private Payment buildNewPayment(double value){
        Payment payment = new Payment();
        payment.setSatus(EPaymentStatus.PENDING);
        payment.setValue(value);

        return payment;
    }
}
