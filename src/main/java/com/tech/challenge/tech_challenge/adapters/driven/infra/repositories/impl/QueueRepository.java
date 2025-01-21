package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import java.util.List;

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
    public Queue save(Queue queueToSave) {
        return queueRepositoryJPA.save(queueToSave);
    }

    @Override
    public List<Queue> findAllNotFinishedItems() {
        return queueRepositoryJPA.findAllNotFinishedItems();
    }
}
