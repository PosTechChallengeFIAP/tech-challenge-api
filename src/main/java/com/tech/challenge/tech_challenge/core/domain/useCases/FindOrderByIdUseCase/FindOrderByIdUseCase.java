package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOrderByIdUseCase implements IFindOrderByIdUseCase {

    @Autowired
    private IOrderRepository orderRepository;

    public Order execute(UUID id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Order.class, String.format("No order with ID %s.", id))
        );
    }
}
