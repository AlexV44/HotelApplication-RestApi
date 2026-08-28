package com.vidilin.hotel.dto;

import java.time.LocalTime;

public class ArrivalTimeDto {
    private LocalTime checkIn;
    private LocalTime checkOut;

    public ArrivalTimeDto() {};

    public ArrivalTimeDto(LocalTime checkIn, LocalTime checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }
}
