package com.tech.challenge.tech_challenge.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToAddPaymentToOrder;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    private static final Consumer<? super OrderItem> OrderItem = null;

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"client_id\"")
    private Client client;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<OrderItem> orderItems;

    @Transient
    private double price;

    @Enumerated(EnumType.ORDINAL)
    private EOrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;
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

    public double getPrice() {
        this.price = 0;

        for (OrderItem orderItem : this.orderItems) {
            this.price += orderItem.getPrice();
        }

        return this.price;
    }

    public void setPayment(Payment newPayment) {
        if(!Objects.isNull(this.payment)) {
            if(this.payment.getId() != newPayment.getId()) {
                throw new UnableToAddPaymentToOrder();
            }

        }

        this.payment = newPayment;

        if(Objects.nonNull(newPayment))
            this.handleStatusAccordingPayment();
    }
    private void handleStatusAccordingPayment() {
        EPaymentStatus paymentStatus = this.payment.getStatus();

        switch (paymentStatus.name()) {
            case "NOT_PAID", "CANCELED":
                this.status = EOrderStatus.CANCELED;
                break;
            case "PAID":
                this.status = EOrderStatus.QUEUE;
                break;
            case "PENDING":
                this.status = EOrderStatus.PAYMENT_PENDING;
                break;
        }
    }

}
