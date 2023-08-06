package com.ml.hotel.util;

public enum RoomStatusEnum {
    AVAILABLE("Available"),
    RESERVED("Reserved"),
    OCCUPIED("Occupied"),
    PAID("Paid"),
    CHECKED_OUT("Checked Out");

    private final String displayName;

    RoomStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
