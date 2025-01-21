package com.tech.challenge.tech_challenge.core.domain.useCases.AddPaymentToOrderUseCase;

import com.tech.challenge.tech_challenge.core.application.dtos.GeneratePaymentLinkRequestDTO;
import com.tech.challenge.tech_challenge.core.application.ports.IPaymentGateway;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Payment;
import com.tech.challenge.tech_challenge.core.domain.useCases.AddPaymentToOrderUseCase.mappers.AddPaymentToOrderMapper;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreatePaymentUseCase.CreatePaymentUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase.UpdateOrderUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdatePaymentUseCase.UpdatePaymentUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddPaymentToOrderUseCase implements IAddPaymentToOrderUseCase {

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private CreatePaymentUseCase createPaymentUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    private IPaymentGateway paymentGateway;

    @Autowired
    private UpdatePaymentUseCase updatePaymentUseCase;

    public Order execute(UUID orderId) {
        Order order = findOrderByIdUseCase.execute(orderId);

        Payment createdPayment = createPaymentUseCase.execute(order.getPrice());
        order.setStatus(EOrderStatus.PAYMENT_PENDING);
        order.setPayment(createdPayment);

        String callbackURL = String.format(
            "http://localhost:8080/order/%s/payment/%s", 
            order.getId().toString(),
            createdPayment.getId().toString()
        );

        List<GeneratePaymentLinkRequestDTO> listToGenerateLink = AddPaymentToOrderMapper.orderToPaymentLinkRequest(order);
        String paymentURL = paymentGateway.generatePaymentLink(listToGenerateLink, callbackURL);
        createdPayment.setPaymentURL(paymentURL);
        updatePaymentUseCase.execute(createdPayment);

        return updateOrderUseCase.execute(order);
    }
}
