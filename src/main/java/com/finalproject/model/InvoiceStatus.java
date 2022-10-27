package com.finalproject.model;

import lombok.Getter;

@Getter
public enum InvoiceStatus {

    REGISTERED(0),PROCESSED(1), SHIPPED(2), EN_ROUTE(3), ARRIVED(4);

    private final int numberOfStep;

    InvoiceStatus(int i) {
        numberOfStep = i;
    }
}
