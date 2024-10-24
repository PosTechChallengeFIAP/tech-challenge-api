package com.tech.challenge.tech_challenge.core.domain.entities;

import org.hibernate.annotations.UuidGenerator;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToChangePaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private double value;

    @Enumerated(EnumType.ORDINAL)
    private EPaymentStatus status;

    public Error validate() {
        if (Objects.isNull(this.value) || this.value == 0) {
            return new Error("Invalid value");
        }

        return null;
    }

    public void setSatus(EPaymentStatus newStatus) {
        if(!Objects.isNull(this.status)) {
            boolean changeIsValid = this.status == EPaymentStatus.PENDING || 
                (this.status == EPaymentStatus.NOT_PAID && newStatus == EPaymentStatus.CANCELED);
            
            if (!changeIsValid) {
                throw new UnableToChangePaymentStatus(this.status.name(), newStatus.name());
            }
        }

        this.status = newStatus;
    }
}
