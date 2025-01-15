package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
@Service
public class EditItemToOrderUseCase {

    @Autowired
    private FindOrderItemByIdUseCase findOrderItemByIdUseCase;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private OrderRepository orderRepository;
    public Order execute(UUID orderId, UUID itemId, OrderItem newOrderItem) throws ResourceNotFoundException, ValidationException {
        Order order = findOrderByIdUseCase.execute(orderId);
        OrderItem oldOrderItem = findOrderItemByIdUseCase.execute(order, itemId);

        if(validateNewOrderItem(newOrderItem, oldOrderItem)){
            order.removeItem(oldOrderItem);
            newOrderItem.setId(oldOrderItem.getId());
            newOrderItem.setProduct(oldOrderItem.getProduct());
            newOrderItem.setOrder(order);
            order.addItem(newOrderItem);
            newOrderItem.validate();
        }else throw new ValidationException("Only the 'quantity' property can be edited for order items.");

        return orderRepository.save(order);
    }

    private boolean validateNewOrderItem(OrderItem newOrderItem, OrderItem oldOrderItem) {
        if(!Objects.isNull(newOrderItem.getId()) &&
                !oldOrderItem.getId().equals(newOrderItem.getId())) return false;

        if(!Objects.isNull(newOrderItem.getProduct()) &&
                !oldOrderItem.getProduct().getId().equals(newOrderItem.getProduct().getId())){
            return false;
        }

        return true;
    }
}
