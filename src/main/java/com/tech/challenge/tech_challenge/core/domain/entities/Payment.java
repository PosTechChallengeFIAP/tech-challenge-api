package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToChangePaymentStatus;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Payment {
    private UUID id;
    private Double value;
    private EPaymentStatus status;
    private String paymentURL;

    public void validate() throws ValidationException {
        if (Objects.isNull(this.value) || this.value == 0) {
            throw new ValidationException("Invalid payment value");
        }
    }

    public void setStatus(EPaymentStatus newStatus) {
        if(!Objects.isNull(this.status)) {
            boolean changeIsValid = this.status == EPaymentStatus.PENDING || 
                (this.status == EPaymentStatus.NOT_PAID && newStatus == EPaymentStatus.CANCELED);
            
            if (!changeIsValid) {
                throw new UnableToChangePaymentStatus(this.status.name(), newStatus.name());
            }
        }

        this.status = newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order )) return false;
        return id != null && id.equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
