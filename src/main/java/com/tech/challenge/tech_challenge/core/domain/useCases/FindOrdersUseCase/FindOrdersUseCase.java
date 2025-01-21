package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrdersUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOrdersUseCase implements IFindOrdersUseCase {

    @Autowired
    private IOrderRepository orderRepository;

    public List<Order> execute(){
        return orderRepository.findAll();
    }

}
