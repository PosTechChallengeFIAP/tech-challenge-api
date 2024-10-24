package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToParseProductCategoryException;

public enum EOrderStatus {
    ORDERING("ordering"),
    PAYMENT_PENDING("payment pending"),
    PAID("paid"),
    PREPARING("preparing"),
    CANCELED("canceled");

    private String value;

    EOrderStatus(String value) {
        this.value = value;
    }

    public EOrderStatus fromValue(String value){
        for(EOrderStatus status : EOrderStatus.values()){
            if(status.value.equals(value)) return status;
        }

        throw new UnableToParseProductCategoryException(value);
    }
}
