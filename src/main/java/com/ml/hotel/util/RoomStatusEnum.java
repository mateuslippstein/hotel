package com.ml.hotel.util;

public enum RoomStatusEnum {
    AVAILABLE("Available"),
    RESERVED("Reserved"),
    OCCUPIED("Occupied");

    private final String value;

    RoomStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

