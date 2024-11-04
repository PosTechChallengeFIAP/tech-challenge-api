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


    public void validate() throws ValidationException {
        if(!hasValidPrice()) {
            throw new ValidationException("Invalid price");
        }

        if(!hasValidOrderItems()) {
            throw new ValidationException("Invalid order items");
        }

        if(!hasValidClient()) {
            throw new ValidationException("Invalid client");
        }

        if(!hasValidPaymentAndStatus()) {
            throw new ValidationException("Invalid status");
        }
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

    public double getPrice() {
        this.price = 0;

        for (OrderItem orderItem : this.orderItems) {
            this.price += orderItem.getPrice();
        }

        return this.price;
    }

    public void addItem(OrderItem orderItem) {
        if(Objects.isNull(this.orderItems)) this.orderItems = new HashSet<>();
        OrderItem orderItemFound = findOrderItem(orderItem);
        
        if (orderItemFound != null) {
            int lastQuantity = orderItemFound.getQuantity();
            orderItemFound.setQuantity(lastQuantity+1);
        } else{
            this.orderItems.add(orderItem);
            orderItem.setOrder(this);
        }

    }

    public void removeItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public void setPayment(Payment newPayment) {
        if(!Objects.isNull(this.payment)) {
            if(this.payment.getId() != newPayment.getId()) {
                throw new UnableToAddPaymentToOrder();
            }

        }
        
        this.payment = newPayment;

        if(Objects.nonNull(newPayment))
            this.handleStatusAccourdingPayment();
    }

    private void handleStatusAccourdingPayment() {
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

    private OrderItem findOrderItem(OrderItem orderItem) {
        return orderItems.stream()
        .filter(item -> item.getId() == orderItem.getId())
        .findFirst()
        .orElse(null);
    }

    private boolean hasValidOrderItems() {
        if(Objects.isNull(orderItems)) return true;

        for (OrderItem item: orderItems) {
            if (item.getId() == null) {
                return false;
            }
        }

        return true;
    }

    private boolean hasValidClient() {
        return Objects.isNull(client) || Objects.nonNull(client.getId());
    }

    private boolean hasValidPrice() {
        return Objects.isNull(orderItems) || orderItems.isEmpty() || getPrice() > 0;
    }

    private boolean hasValidPaymentAndStatus() {
        if(!Objects.isNull(payment)) {
            EPaymentStatus paymentStatus = payment.getStatus();

            switch (paymentStatus){
                case PENDING -> {
                    return status == EOrderStatus.PAYMENT_PENDING;
                }
                case PAID -> {
                    return (
                        status == EOrderStatus.PAID ||
                        status == EOrderStatus.PREPARING ||
                        status == EOrderStatus.QUEUE
                    );
                }
                case CANCELED, NOT_PAID -> {
                    return status == EOrderStatus.CANCELED;
                }
                default -> {
                    return false;
                }
            }

        }else{
            return status == EOrderStatus.ORDERING;
        }
    }
}
