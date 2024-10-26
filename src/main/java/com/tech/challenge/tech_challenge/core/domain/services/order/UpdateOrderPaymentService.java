package com.tech.challenge.tech_challenge.core.domain.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.QueueRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

import java.util.UUID;

@Service
public class UpdateOrderPaymentService {
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    QueueRepository queueRepository;

    public Payment handle(UUID orderId, UUID paymentId, EPaymentStatus status) throws Exception {
        Payment payment = getPaymentById(paymentId);
        payment.setSatus(status);
        validatePayment(payment);

        Order order = getOrderById(orderId);
        order.setPayment(payment);
        validateOrder(order);

        Payment updatedPayment = paymentRepository.save(payment);
        orderRepository.save(order);

        if(status == EPaymentStatus.PAID) {
            Queue queue = new Queue();
            queue.setOrder(order);
            queue.setStatus(EQueueStatus.RECEIVED);
            queueRepository.save(queue);
        }
        
        return updatedPayment;
    }

    private Payment getPaymentById(UUID id) throws Exception {
        return paymentRepository.findById(id).orElseThrow(
            () -> new Exception("Unable to find payment")
        );
    }

    private Order getOrderById(UUID id) throws Exception {
        return orderRepository.findById(id).orElseThrow(
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
