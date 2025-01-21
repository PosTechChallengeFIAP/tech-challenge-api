package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.DTOs;

import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateItemQueueUseCaseRequest {
    private Integer queueId;
    private EQueueStatus status;
}
