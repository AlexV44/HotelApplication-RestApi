package com.vidilin.hotel.controller;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.service.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}