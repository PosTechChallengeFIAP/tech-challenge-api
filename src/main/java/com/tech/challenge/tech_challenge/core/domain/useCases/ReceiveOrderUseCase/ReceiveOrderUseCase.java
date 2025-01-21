package com.tech.challenge.tech_challenge.core.domain.useCases.ReceiveOrderUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveOrderUseCase implements IReceiveOrderUseCase {

    @Autowired
    private IQueueRepository queueRepository;

    public Queue execute(Order order) {
        Queue queue = new Queue();
        queue.setOrder(order);
        queue.setStatus(EQueueStatus.RECEIVED);

        return queueRepository.save(queue);
    }
}
