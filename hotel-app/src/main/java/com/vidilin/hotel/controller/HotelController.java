package com.vidilin.hotel.controller;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import com.vidilin.hotel.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels")
    public List<HotelSummaryDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public HotelDetailDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryDto> saveHotel(@RequestBody SaveHotelDto dto) {
        HotelSummaryDto response = hotelService.saveHotel(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/hotels/{id}/amenities")
    public void addAmenitiesById(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenitiesById(id, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> groupHotelsByParam(@PathVariable String param) {
        return hotelService.groupHotelsByParam(param);
    }

    @GetMapping("/search")
    public List<HotelSummaryDto> searchHotels(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String brand,
                                              @RequestParam(required = false) String city,
                                              @RequestParam(required = false) String country,
                                              @RequestParam(required = false) String amenities) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }
}