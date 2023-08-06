package com.ml.hotel.util;

public enum PaymentMethodEnum {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PIX("PIX");

    private final String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
