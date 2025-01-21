package com.tech.challenge.tech_challenge.core.domain.useCases;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.EQueueStatus;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.repositories.IQueueRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.UpdateItemQueueUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.DTOs.UpdateItemQueueUseCaseRequest;

@SpringBootTest
public class UpdateItemQueueUseCaseTest {
    @MockBean
    private IQueueRepository queueRepository;

    @Autowired
    private UpdateItemQueueUseCase updateItemQueueUseCase;
    
    @Test
    public void updateItemQueueTest_Success() {
        Integer mockedQueueItemId = 1;

        Queue mockedQueueItem = new Queue();
        mockedQueueItem.setId(mockedQueueItemId);
        mockedQueueItem.setStatus(EQueueStatus.RECEIVED);

        when(queueRepository.findById(mockedQueueItemId)).thenReturn(Optional.of(mockedQueueItem));
        when(queueRepository.save(mockedQueueItem)).thenReturn(mockedQueueItem);

        EQueueStatus statusToTest = EQueueStatus.PREPARING;
        UpdateItemQueueUseCaseRequest useCaseRequest = new UpdateItemQueueUseCaseRequest(mockedQueueItemId, statusToTest);

        Queue queueItemResult = updateItemQueueUseCase.execute(useCaseRequest);

        assert(queueItemResult.getStatus().equals(statusToTest));
        assert(queueItemResult).equals(mockedQueueItem);
    }

    @Test
    public void updateItemQueueTestNotFound_Error() {
        Integer mockedQueueItemId = 1;

        when(queueRepository.findById(mockedQueueItemId)).thenReturn(Optional.empty());

        EQueueStatus statusToTest = EQueueStatus.PREPARING;
        UpdateItemQueueUseCaseRequest useCaseRequest = new UpdateItemQueueUseCaseRequest(mockedQueueItemId, statusToTest);

        assertThrows(
            ValidationException.class,
            () -> updateItemQueueUseCase.execute(useCaseRequest)
        );
    }
}
