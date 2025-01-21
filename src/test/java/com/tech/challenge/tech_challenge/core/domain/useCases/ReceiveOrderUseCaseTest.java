package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase.ReceiveOrderUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReceiveOrderUseCaseTest {
    @MockBean
    private IQueueRepository queueRepository;

    @Autowired
    private ReceiveOrderUseCase receiveOrderUseCase;
    @Test
    public void receiveOrderTest() throws Exception {
        Order order = mock(Order.class);

        when(queueRepository.save(any(Queue.class))).thenAnswer(i -> i.getArguments()[0]);

        Queue queue = receiveOrderUseCase.execute(order);

        assertEquals(queue.getStatus(), EQueueStatus.RECEIVED);
    }
}
