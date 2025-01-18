package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.QueueRepositoryJPA;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers.QueueEntityMapper;
import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.QueueEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;

@Repository
public class QueueRepository implements IQueueRepository {

    @Autowired
    private QueueRepositoryJPA queueRepositoryJPA;

    @Override
    public Queue save(Queue queueToSave) {
        QueueEntity queueEntityToSave = toEntity(queueToSave);
        QueueEntity savedQueueEntity = queueRepositoryJPA.save(queueEntityToSave);
        Queue queue = toDomain(savedQueueEntity);
        return queue;
    }

    @Override
    public List<Queue> findAllNotFinishedItems() {
        List<QueueEntity> queuesEntity = queueRepositoryJPA.findAllNotFinishedItems();
        List<Queue> queues = toDomain(queuesEntity);
        return queues;
    }

    Queue toDomain(QueueEntity queueEntity) {
        return QueueEntityMapper.toDomain(queueEntity);
    }

    List<Queue> toDomain(List<QueueEntity> queuesEntity) {
        List<Queue> queues = new ArrayList<>();

        queuesEntity.forEach(queueEntity -> {
            Queue queue = toDomain(queueEntity);
            queues.add(queue);
        });

        return queues;
    }

    QueueEntity toEntity(Queue queue) {
        return QueueEntityMapper.toEntity(queue);
    }
}
