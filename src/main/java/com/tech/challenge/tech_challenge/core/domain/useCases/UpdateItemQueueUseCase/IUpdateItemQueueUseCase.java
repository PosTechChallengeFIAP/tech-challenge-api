package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.DTOs.UpdateItemQueueUseCaseRequest;

public interface IUpdateItemQueueUseCase {
    Queue execute(UpdateItemQueueUseCaseRequest request);
}
