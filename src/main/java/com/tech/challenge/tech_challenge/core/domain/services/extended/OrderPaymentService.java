package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.PaymentService;
import com.tech.challenge.tech_challenge.core.domain.services.QueueService;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QueueService queueService;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    public Order addPaymentToOrder(UUID orderId) throws ResourceNotFoundException, ValidationException {
        Order order = findOrderByIdUseCase.execute(orderId);

        Payment createdPayment = paymentService.createNewPayment(order.getPrice());
        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);

        return updateOrderUseCase.execute(order);
    }

    public Payment updateOrderPayment(UUID orderId, UUID paymentId, EPaymentStatus status) throws ResourceNotFoundException, ValidationException {
        Payment payment = paymentService.getById(paymentId);
        payment.setSatus(status);

        Order order = findOrderByIdUseCase.execute(orderId);
        order.setPayment(payment);

        Payment updatedPayment = paymentService.update(payment);
        updateOrderUseCase.execute(order);

        if(status == EPaymentStatus.PAID) {
            queueService.receiveOrder(order);
        }

        return updatedPayment;
    }


}
