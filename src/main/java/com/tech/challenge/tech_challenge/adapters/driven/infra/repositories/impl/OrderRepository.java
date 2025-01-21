package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.OrderRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

@Repository
public class OrderRepository implements IOrderRepository {

    @Autowired
    private OrderRepositoryJPA orderRepositoryJPA;

    @Override
    public Order save(Order orderToSave) {
        return orderRepositoryJPA.save(orderToSave);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepositoryJPA.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepositoryJPA.findAll();
    }
}
