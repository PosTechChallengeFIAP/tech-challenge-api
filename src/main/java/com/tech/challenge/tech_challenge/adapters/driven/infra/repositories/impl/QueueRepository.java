package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.QueueRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;

@Repository
public class QueueRepository implements IQueueRepository {

    @Autowired
    private QueueRepositoryJPA queueRepositoryJPA;

    @Override
    public Optional<Queue> findById(Integer id) {
        Optional<Queue> queueItem = queueRepositoryJPA.findById(id);
        return queueItem;
    }

    @Override
    public Queue save(Queue queueToSave) {
        return queueRepositoryJPA.save(queueToSave);
    }

    @Override
    public List<Queue> findAllNotFinishedItems() {
        return queueRepositoryJPA.findAllNotFinishedItems();
    }
}
