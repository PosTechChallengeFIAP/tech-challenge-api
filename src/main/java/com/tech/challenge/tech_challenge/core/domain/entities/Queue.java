package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.Date;
import java.util.Objects;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToChangeQueueStatus;

import lombok.Data;

@Data
public class Queue {
    private Integer id;
    private EQueueStatus status;
    private Order order;
    private Date createdAt;
    private Date updatedAt;

    public void setStatus(EQueueStatus newStatus) {
        if(!Objects.isNull(this.status)) {
            EQueueStatus oldStatus = this.status;

            boolean caseReceivedShouldBePreparingDoneOrFinished = 
                oldStatus == EQueueStatus.RECEIVED &&
                (
                    newStatus == EQueueStatus.PREPARING ||
                    newStatus == EQueueStatus.DONE ||
                    newStatus == EQueueStatus.FINISHED 
                );
            boolean casePreparingShouldBeDoneOrFinished =
                oldStatus == EQueueStatus.PREPARING && 
                (
                    newStatus == EQueueStatus.DONE ||
                    newStatus == EQueueStatus.FINISHED 
                );
            boolean caseDoneShouldBeFinished =
                oldStatus == EQueueStatus.DONE &&
                newStatus == EQueueStatus.FINISHED;
            
            boolean valid = 
                caseReceivedShouldBePreparingDoneOrFinished ||
                casePreparingShouldBeDoneOrFinished ||
                caseDoneShouldBeFinished;

            if (!valid) {
                throw new UnableToChangeQueueStatus(oldStatus.name(), newStatus.name());
            }
        }

        this.status = newStatus;
    }
}
