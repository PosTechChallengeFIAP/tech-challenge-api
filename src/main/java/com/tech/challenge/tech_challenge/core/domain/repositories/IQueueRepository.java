package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

public interface IQueueRepository {
    List<Queue> findAllNotFinishedItems();
    Queue save(Queue queue);
}
