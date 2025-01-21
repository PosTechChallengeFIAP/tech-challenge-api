package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.DTOs.UpdateItemQueueUseCaseRequest;

@Service
public class UpdateItemQueueUseCase implements IUpdateItemQueueUseCase {

    @Autowired
    private IQueueRepository queueRepository;

    @Override
    public Queue execute(UpdateItemQueueUseCaseRequest request) {
        Integer queueId = request.getQueueId();
        EQueueStatus newStatus = request.getStatus();

        Optional<Queue> queueItemFromRP = queueRepository.findById(queueId);

        if (queueItemFromRP.isEmpty()) {
            throw new ValidationException("Item not found.");
        }

        Queue queueItem = queueItemFromRP.get();
        queueItem.setStatus(newStatus);

        Queue savedQueueItem = queueRepository.save(queueItem);

        return savedQueueItem;
    }
}
