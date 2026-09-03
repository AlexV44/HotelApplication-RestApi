package com.vidilin.hotel.controller.api;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSearchRequest;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface HotelApi {

    @Operation(summary = "Get all hotels", description = "Retrieves a summary list of all available hotels.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of hotels")
    @Tag(name = "Hotels: Read Operations", description = "Getting and searching hotels")
    List<HotelSummaryDto> getAllHotels();

    @Operation(summary = "Get hotel by ID", description = "Retrieves detailed information about a specific hotel by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved hotel details")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @Tag(name = "Hotels: Read Operations")
    HotelDetailDto getHotelById(@Parameter(
            name = "id",
            description = "Unique identifier of the hotel",
            required = true
    ) @PathVariable Long id);

    @Operation(summary = "Create a new hotel", description = "Saves a new hotel entity to the system.")
    @ApiResponse(responseCode = "201", description = "Hotel successfully created")
    @ApiResponse(responseCode = "400", description = "Hotel not saved")
    @Tag(name = "Hotels: Write Operations", description = "Creating and editing hotels")
    ResponseEntity<HotelSummaryDto> saveHotel(@RequestBody SaveHotelDto dto);

    @Operation(summary = "Add amenities to hotel", description = "Adds a list of new amenities to an existing hotel by its ID.")
    @ApiResponse(responseCode = "200", description = "Amenities successfully added")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @Tag(name = "Hotels: Write Operations")
    void addAmenitiesById(@PathVariable Long id, @RequestBody List<String> amenities);

    @Operation(summary = "Group hotels by parameter", description = "Returns a histogram count of hotels grouped by specified parameter (brand, city, country, amenities).")
    @ApiResponse(responseCode = "200", description = "Successfully generated hotel histogram")
    @ApiResponse(responseCode = "400", description = "Invalid grouping parameter provided")
    @Tag(name = "Hotels: Read Operations")
    Map<String, Long> groupHotelsByParam(@Parameter(
            name = "param",
            description = "Parameter to group by (brand, city, country, amenities)",
            required = true
    ) @PathVariable String param);

    @Operation(summary = "Search hotels", description = "Searches for hotels matching specified filter criteria.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching hotels")
    @ApiResponse(responseCode = "400", description = "No search criteria provided")
    @Tag(name = "Hotels: Read Operations")
    List<HotelSummaryDto> searchHotels(@ParameterObject HotelSearchRequest request);
}
