package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Criteria parameters for searching hotels")
public record HotelSearchRequest(
        @Schema(description = "Hotel name")
        String name,
        @Schema(description = "Filter by hotel brand")
        String brand,
        @Schema(description = "Filter by city")
        String city,
        @Schema(description = "Filter by country")
        String country,
        @Parameter(
                description = "List of amenities",
                array = @ArraySchema(schema = @Schema(type = "string"))
        )
        List<String> amenities
) {
    public boolean isEmpty() {
        return (name == null || name.isBlank())
                && (brand == null || brand.isBlank())
                && (city == null || city.isBlank())
                && (country == null || country.isBlank())
                && (amenities == null || amenities.isEmpty());
    }
}
