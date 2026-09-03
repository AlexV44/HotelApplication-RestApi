package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO for detailed hotel information")
public record HotelDetailDto(
        @Schema(description = "Unique hotel identifier")
        Long id,
        @Schema(description = "Hotel name")
        String name,
        @Schema(description = "Hotel description")
        String description,
        @Schema(description = "Hotel brand")
        String brand,
        @Schema(description = "Hotel address details")
        AddressDto address,
        @Schema(description = "Contact details")
        ContactsDto contacts,
        @Schema(description = "Check-in and check-out times")
        ArrivalTimeDto arrivalTime,
        @Schema(description = "List of hotel amenities")
        List<String> amenities
) {}