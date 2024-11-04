package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.services.PaymentService;
import com.tech.challenge.tech_challenge.core.domain.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentService extends OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QueueService queueService;

    public Order addPaymentToOrder(UUID orderId) throws ResourceNotFoundException, ValidationException {
        Order order = getById(orderId);

        Payment createdPayment = paymentService.createNewPayment(order.getPrice());
        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);

        return update(order);
    }

    public Payment updateOrderPayment(UUID orderId, UUID paymentId, EPaymentStatus status) throws ResourceNotFoundException, ValidationException {
        Payment payment = paymentService.getById(paymentId);
        payment.setSatus(status);

        Order order = getById(orderId);
        order.setPayment(payment);

        Payment updatedPayment = paymentService.update(payment);
        update(order);

        if(status == EPaymentStatus.PAID) {
            queueService.receiveOrder(order);
        }

        return updatedPayment;
    }


}
