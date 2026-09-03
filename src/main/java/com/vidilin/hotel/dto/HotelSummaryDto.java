package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for summary hotel information")
public record HotelSummaryDto(
        @Schema(description = "Unique hotel identifier")
        Long id,
        @Schema(description = "Hotel name")
        String name,
        @Schema(description = "Short description")
        String description,
        @Schema(description = "Full formatted address string")
        String address,
        @Schema(description = "Contact phone number")
        String phone
) {}