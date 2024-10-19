package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.OrderRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> list(){
        return orderRepository.findAll();
    }

    public Order getById(UUID id) throws Exception {
        return orderRepository.findById(id).orElseThrow(
                () -> new Exception("Unable to Find Order")
        );
    }

    public Order create(Order order){
        order.setOrderItems(Collections.emptySet());
        return orderRepository.save(order);
    }

    public Order addItem(OrderItem orderItem) throws Exception {
        Order order = getById(orderItem.getOrder().getId());
        order.addItem(orderItem);

        return orderRepository.save(order);
    }
}
