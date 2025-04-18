package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.repositories.IOrderRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateOrderUseCase.UpdateOrderUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateOrderUseCaseTest {

    @MockBean
    private IOrderRepository orderRepository;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @Test
    public void updateTest_Success() throws ValidationException {
        Order order = mock(Order.class);

        when(orderRepository.save(order)).thenReturn(order);

        Order orderResult = updateOrderUseCase.execute(order);

        assertEquals(order,orderResult);
    }

    @Test
    public void updateTest_Exception() throws Exception {
        Order order = mock(Order.class);

        when(orderRepository.save(order)).thenReturn(order);
        doThrow(ValidationException.class).doNothing().when(order).validate();

        assertThrows(ValidationException.class, ()->{
            updateOrderUseCase.execute(order);
        });
    }
}
