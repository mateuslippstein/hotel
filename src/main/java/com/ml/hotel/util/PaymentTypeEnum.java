package com.ml.hotel.util;

public enum PaymentTypeEnum {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PIX("PIX");

    private final String displayName;

    PaymentTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
