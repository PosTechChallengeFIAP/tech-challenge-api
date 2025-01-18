package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.mappers;


import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.QueueEntity;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

public class QueueEntityMapper {
    public static Queue toDomain(QueueEntity queueEntity) {
        Queue queue = new Queue();
        queue.setId(queueEntity.getId());
        queue.setStatus(queueEntity.getStatus());
        queue.setOrder(
            queueEntity.getOrder() != null ? OrderEntityMapper.toDomain(queueEntity.getOrder()) : null
        );
        queue.setCreatedAt(queueEntity.getCreatedAt());
        queue.setUpdatedAt(queueEntity.getUpdatedAt());
        return queue;
    }

    public static QueueEntity toEntity(Queue queue) {
        QueueEntity queueEntity = new QueueEntity();
        queueEntity.setId(queue.getId());
        queueEntity.setStatus(queue.getStatus());
        queueEntity.setOrder(
            queue.getOrder() != null ? OrderEntityMapper.toEntity(queue.getOrder()) : null
        );
        queueEntity.setCreatedAt(queue.getCreatedAt());
        queueEntity.setUpdatedAt(queue.getUpdatedAt());
        return queueEntity;
    }
}
