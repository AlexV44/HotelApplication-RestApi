package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "DTO for check-in and check-out times")
public record ArrivalTimeDto(
        @Schema(description = "Check-in time")
        LocalTime checkIn,
        @Schema(description = "Check-out time")
        LocalTime checkOut
) {}