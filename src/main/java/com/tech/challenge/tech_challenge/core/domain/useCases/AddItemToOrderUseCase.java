package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AddItemToOrderUseCase {

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @Autowired
    private FindProductByIdUseCase findProductByIdUseCase;

    @Autowired
    private OrderRepository orderRepository;

    public Order execute(UUID orderId, OrderItem orderItem) throws ResourceNotFoundException, ValidationException {
        Order order = findOrderByIdUseCase.execute(orderId);
        order.addItem(orderItem);
        orderItem.setOrder(order);
        orderItem.validate();

        if(!checkIfProductIsActive(orderItem)){
            throw new IllegalArgumentException("Product is Inactive.");
        }

        return orderRepository.save(order);
    }

    private boolean checkIfProductIsActive(OrderItem orderItem) throws ValidationException, ResourceNotFoundException {
        if(Objects.isNull(orderItem.getProduct()) ||
                Objects.isNull(orderItem.getProduct().getId()))
            throw new ValidationException("Product is Invalid.");

        Product product = findProductByIdUseCase.execute(orderItem.getProduct().getId());

        return product.getActive();
    }
}
