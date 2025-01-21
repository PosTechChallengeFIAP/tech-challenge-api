package com.tech.challenge.tech_challenge.core.domain.useCases.FindOrdersUseCase;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.Order;

public interface IFindOrdersUseCase {
    List<Order> execute();
}
