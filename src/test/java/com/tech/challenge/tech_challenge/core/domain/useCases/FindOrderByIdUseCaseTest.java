package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindOrderByIdUseCase.FindOrderByIdUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindOrderByIdUseCaseTest {

    @MockBean
    private IOrderRepository orderRepository;

    @Autowired
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Test
    public void getByIdTest_Success() throws Exception {
        Order order = new Order();

        order.setId(UUID.randomUUID());

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order orderResult = findOrderByIdUseCase.execute(order.getId());

        assertEquals(order, orderResult);
    }

    @Test
    public void getByIdTest_Exception(){
        UUID id = UUID.randomUUID();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            findOrderByIdUseCase.execute(id);
        });
    }

}
