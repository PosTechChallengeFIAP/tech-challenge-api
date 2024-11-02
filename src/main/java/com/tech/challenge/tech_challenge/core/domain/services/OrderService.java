package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EOrderStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public List<Order> list(){
        return orderRepository.findAll();
    }

    public Order getById(UUID id) throws Exception {
        return orderRepository.findById(id).orElseThrow(
                () -> new Exception("Unable to Find Order")
        );
    }

    public Order update(Order order) throws Exception {
        validateOrder(order);

        return orderRepository.save(order);
    }

    public Order create(Order order){
        order.setOrderItems(Collections.emptySet());
        order.setStatus(EOrderStatus.ORDERING);
        return orderRepository.save(order);
    }

    public Order addItem(UUID orderId, OrderItem orderItem) throws Exception {
        Order order = getById(orderId);

        if(!checkIfProductIsActive(orderItem)){
            throw new IllegalArgumentException("Product is Inactive.");
        }

        order.addItem(orderItem);
        return orderRepository.save(order);
    }

    public Order removeItem(UUID orderId, UUID itemId) throws Exception {
        Order order = getById(orderId);
        order.removeItem(getOrdemItemById(order, itemId));

        return orderRepository.save(order);
    }

    public Order editItem(UUID orderId, UUID itemId, OrderItem newOrderItem) throws Exception {
        Order order = getById(orderId);
        OrderItem oldOrderItem = getOrdemItemById(order, itemId);

        if(validateNewOrderItem(newOrderItem, oldOrderItem)){
            order.removeItem(oldOrderItem);
            newOrderItem.setId(oldOrderItem.getId());
            newOrderItem.setProduct(oldOrderItem.getProduct());
            order.addItem(newOrderItem);
        }else throw new Exception("Only the 'quantity' property can be edited for order items.");

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

    public void validateOrder(Order order) throws Exception {
        Error err = order.validate();
        if(err != null) {
            throw err;
        }
    }

    private boolean checkIfProductIsActive(OrderItem orderItem) throws Exception {
        if(Objects.isNull(orderItem.getProduct()) ||
                Objects.isNull(orderItem.getProduct().getId()))
            throw new IllegalArgumentException("Product is Invalid.");

        Product product = productService.getById(orderItem.getProduct().getId());

        return product.getActive();
    }
}

