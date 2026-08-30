package com.vidilin.hotel.controller;

import com.vidilin.hotel.controller.api.HotelApi;
import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSearchRequest;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import com.vidilin.hotel.enums.HotelParams;
import com.vidilin.hotel.service.HotelService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController implements HotelApi {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    @GetMapping("/hotels")
    public List<HotelSummaryDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Override
    @GetMapping("/hotels/{id}")
    public HotelDetailDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Override
    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryDto> saveHotel(@RequestBody SaveHotelDto dto) {
        var response = hotelService.saveHotel(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/hotels/{id}/amenities")
    public void addAmenitiesById(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenitiesById(id, amenities);
    }

    @Override
    @GetMapping("/histogram/{param}")
    public Map<String, Long> groupHotelsByParam(@PathVariable String param) {
        return hotelService.groupHotelsByParam(HotelParams.fromString(param));
    }

    @Override
    @GetMapping("/search")
    public List<HotelSummaryDto> searchHotels(@ParameterObject HotelSearchRequest request) {
        return hotelService.searchHotels(request);
    }
}