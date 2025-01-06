package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.EPaymentStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidateOrder {

    public void validate(Order order) throws ValidationException {
        if(!hasValidPrice(order)) {
            throw new ValidationException("Invalid price");
        }

        if(!hasValidOrderItems(order)) {
            throw new ValidationException("Invalid order items");
        }

        if(!hasValidClient(order)) {
            throw new ValidationException("Invalid client");
        }

        if(!hasValidPaymentAndStatus(order)) {
            throw new ValidationException("Invalid status");
        }
    }

    private boolean hasValidOrderItems(Order order) {
        if(Objects.isNull(order.getOrderItems())) return true;

        for (OrderItem item: order.getOrderItems()) {
            if (item.getId() == null) {
                return false;
            }
        }

        return true;
    }

    private boolean hasValidClient(Order order) {
        return Objects.isNull(order.getClient()) || Objects.nonNull(order.getClient().getId());
    }

    private boolean hasValidPrice(Order order) {
        return Objects.isNull(order.getOrderItems()) || order.getOrderItems().isEmpty() || order.getPrice() > 0;
    }

    private boolean hasValidPaymentAndStatus(Order order) {

        EOrderStatus status = order.getStatus();

        if(!Objects.isNull(order.getPayment())) {
            EPaymentStatus paymentStatus = order.getPayment().getStatus();

            switch (paymentStatus){
                case PENDING -> {
                    return  status == EOrderStatus.PAYMENT_PENDING;
                }
                case PAID -> {
                    return (
                            status == EOrderStatus.PAID ||
                                    status == EOrderStatus.PREPARING ||
                                    status == EOrderStatus.QUEUE
                    );
                }
                case CANCELED, NOT_PAID -> {
                    return status == EOrderStatus.CANCELED;
                }
                default -> {
                    return false;
                }
            }

        }else{
            return status == EOrderStatus.ORDERING;
        }
    }
}
