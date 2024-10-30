package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.QueueRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public Queue receiveOrder(Order order){
        Queue queue = new Queue();
        queue.setOrder(order);
        queue.setStatus(EQueueStatus.RECEIVED);

        return queueRepository.save(queue);
    }
}
