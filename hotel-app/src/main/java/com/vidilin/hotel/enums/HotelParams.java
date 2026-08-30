package com.vidilin.hotel.enums;

import com.vidilin.hotel.exception.BadRequestException;

import java.util.Arrays;

public enum HotelParams {
    BRAND("brand"),
    CITY("city"),
    COUNTRY("country"),
    AMENITIES("amenities");

    private final String value;

    HotelParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HotelParams fromString(String text) {
        return Arrays.stream(HotelParams.values())
                .filter(param -> param.value.equalsIgnoreCase(text))
                .findFirst().orElseThrow(() -> new BadRequestException("Invalid parameter" + text));
    }
}