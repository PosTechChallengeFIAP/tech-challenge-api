package com.tech.challenge.tech_challenge.core.domain.services.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

@Service
public class AddPaymentToOrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public Order handle(UUID orderId) throws Exception {
        Order order = this.getOrderById(orderId);

        Payment payment = new Payment();
        payment.setSatus(EPaymentStatus.PENDING);
        payment.setValue(order.getPrice());
        this.validatePayment(payment);
        Payment createdPayment = paymentRepository.save(payment);

        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);
        this.validateOrder(order);

        Order updatedOrder = orderRepository.save(order);
        return updatedOrder;
    }

    private Order getOrderById(UUID orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new Exception("Unable to find order")
        );
    }

    private void validatePayment(Payment payment) throws Exception {
        Error err = payment.validate();
        if(err != null) {
            throw err;
        }
    }

    private void validateOrder(Order order) throws Exception {
        Error err = order.validate();
        if(err != null) {
            throw err;
        }
    }
}
