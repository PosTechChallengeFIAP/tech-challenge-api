package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToChangeQueueStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "queue")
@EntityListeners(AuditingEntityListener.class)
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "queue_seq")
    @SequenceGenerator(name = "queue_seq", sequenceName = "queue_seq", allocationSize = 1)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private EQueueStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
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
