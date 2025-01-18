package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import org.hibernate.annotations.UuidGenerator;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToChangePaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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

    @Column(nullable = false, name = "`value`")
    private Double value;

    @Enumerated(EnumType.ORDINAL)
    private EPaymentStatus status;

    @Column(name = "payment_url")
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
