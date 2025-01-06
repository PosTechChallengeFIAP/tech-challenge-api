package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public List<Order> list(){
        return orderRepository.findAll();
    }

    public Order getById(UUID id) throws ResourceNotFoundException {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Order.class, String.format("No order with ID %s.", id))
        );
    }

    public Order update(Order order) throws ValidationException {
       order.validate();

       return orderRepository.save(order);
    }

    public Order create(Order order) throws ValidationException{
        order.setOrderItems(Collections.emptySet());
        order.setStatus(EOrderStatus.ORDERING);
        order.validate();

        return orderRepository.save(order);
    }

    public Order addItem(UUID orderId, OrderItem orderItem) throws ResourceNotFoundException, ValidationException {
        Order order = getById(orderId);
        order.addItem(orderItem);
        orderItem.setOrder(order);
        ValidateOrderItem.validate(orderItem);

        if(!checkIfProductIsActive(orderItem)){
            throw new IllegalArgumentException("Product is Inactive.");
        }

        return orderRepository.save(order);
    }

    public Order removeItem(UUID orderId, UUID itemId) throws ResourceNotFoundException, NoSuchElementException {
        Order order = getById(orderId);
        order.removeItem(getOrdemItemById(order, itemId));

        return orderRepository.save(order);
    }

    public Order editItem(UUID orderId, UUID itemId, OrderItem newOrderItem) throws ResourceNotFoundException, ValidationException {
        Order order = getById(orderId);
        OrderItem oldOrderItem = getOrdemItemById(order, itemId);

        if(validateNewOrderItem(newOrderItem, oldOrderItem)){
            order.removeItem(oldOrderItem);
            newOrderItem.setId(oldOrderItem.getId());
            newOrderItem.setProduct(oldOrderItem.getProduct());
            newOrderItem.setOrder(order);
            order.addItem(newOrderItem);
            ValidateOrderItem.validate(newOrderItem);
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

    private OrderItem getOrdemItemById(Order order, UUID orderItemId){
        return order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst()
                .orElseThrow();
    }

    private boolean checkIfProductIsActive(OrderItem orderItem) throws ValidationException, ResourceNotFoundException {
        if(Objects.isNull(orderItem.getProduct()) ||
                Objects.isNull(orderItem.getProduct().getId()))
            throw new ValidationException("Product is Invalid.");

        Product product = productService.getById(orderItem.getProduct().getId());

        return product.getActive();
    }
}

