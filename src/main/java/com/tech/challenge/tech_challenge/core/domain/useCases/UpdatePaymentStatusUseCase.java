package com.tech.challenge.tech_challenge.core.domain.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;

@Service
public class UpdatePaymentStatusUseCase {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiveOrderUseCase receiveOrderUseCase;

    public Payment execute(UUID orderId, UUID paymentId, String paymentStatus) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        
        if (paymentStatus.contains("success")) {
            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setPayment(payment);

            receiveOrderUseCase.execute(order);
            payment.setStatus(EPaymentStatus.PAID);

            Payment updatedPayment = paymentRepository.save(payment);
            orderRepository.save(order);

            return updatedPayment;
        }

        return payment;
    }
}
