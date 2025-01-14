package com.tech.challenge.tech_challenge.core.domain.services.extended;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.services.OrderService;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindPaymentByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentService extends OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CreatePaymentUseCase createPaymentUseCase;

    @Autowired
    private FindPaymentByIdUseCase findPaymentByIdUseCase;

    @Autowired
    private UpdatePaymentUseCase updatePaymentUseCase;

    @Autowired
    private ReceiveOrderUseCase receiveOrderUseCase;

    public Order addPaymentToOrder(UUID orderId) throws ResourceNotFoundException, ValidationException {
        Order order = getById(orderId);

        Payment createdPayment = createPaymentUseCase.execute(order.getPrice());
        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);

        return update(order);
    }

    public Payment updateOrderPayment(UUID orderId, UUID paymentId, EPaymentStatus status) throws ResourceNotFoundException, ValidationException {
        Payment payment = findPaymentByIdUseCase.execute(paymentId);
        payment.setSatus(status);

        Order order = getById(orderId);
        order.setPayment(payment);

        Payment updatedPayment = updatePaymentUseCase.execute(payment);
        update(order);

        if(status == EPaymentStatus.PAID) {
            receiveOrderUseCase.execute(order);
        }

        return updatedPayment;
    }


}
