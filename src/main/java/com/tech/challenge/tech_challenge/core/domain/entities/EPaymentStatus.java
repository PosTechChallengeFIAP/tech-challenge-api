package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.UnableToParsePaymentStatusException;

public enum EPaymentStatus {
    PENDING("pending"),
    PAID("paid"),
    NOT_PAID("not_paid"),
    CANCELED("canceled");

    private String value;

    EPaymentStatus(String value) {
        this.value = value;
    }

    public EPaymentStatus fromValue(String value){
        for(EPaymentStatus status : EPaymentStatus.values()){
            if(status.value.equals(value)) return status;
        }

        throw new UnableToParsePaymentStatusException(value);
    }
}
