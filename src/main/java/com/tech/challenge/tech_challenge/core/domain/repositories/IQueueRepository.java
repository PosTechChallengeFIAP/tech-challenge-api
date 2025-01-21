package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

public interface IQueueRepository {
    Optional<Queue> findById(Integer id);
    List<Queue> findAllNotFinishedItems();
    Queue save(Queue queue);
}
