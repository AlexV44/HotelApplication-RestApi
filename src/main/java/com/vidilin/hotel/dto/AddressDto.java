package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for address creation")
public record AddressDto(
        @Schema(description = "House number")
    Integer houseNumber,
        @Schema(description = "Street")
    String street,
        @Schema(description = "City")
    String city,
        @Schema(description = "Country")
    String country,
        @Schema(description = "Postcode")
    String postCode
) {}
