package com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentStatusUseCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.repositories.IPaymentRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase.ReceiveOrderUseCase;

@Service
public class UpdatePaymentStatusUseCase implements IUpdatePaymentStatusUseCase {
    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ReceiveOrderUseCase receiveOrderUseCase;

    public Payment execute(UUID orderId, UUID paymentId, String paymentStatus) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        
        if (paymentStatus.contains("success")) {
            payment.setStatus(EPaymentStatus.PAID);

            Order order = orderRepository.findById(orderId).orElseThrow();
            order.setPayment(payment);

            Payment updatedPayment = paymentRepository.save(payment);
            orderRepository.save(order);
            
            receiveOrderUseCase.execute(order);

            return updatedPayment;
        }

        return payment;
    }
}
