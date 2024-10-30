package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.PaymentRepository;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.QueueRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @MockBean
    private QueueRepository queueRepository;

    @Test
    public void receiveOrderTest() throws Exception {
        Order order = mock(Order.class);

        when(queueRepository.save(any(Queue.class))).thenAnswer(i -> i.getArguments()[0]);

        Queue queue = queueService.receiveOrder(order);

        assertEquals(queue.getStatus(), EQueueStatus.RECEIVED);
    }
}
