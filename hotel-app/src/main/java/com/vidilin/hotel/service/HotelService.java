package com.vidilin.hotel.service;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;

import java.util.List;

public interface HotelService {
    List<HotelSummaryDto> getAllHotels();
    HotelDetailDto getHotelById(Long id);
    HotelDetailDto createHotel(HotelDetailDto dto);
    HotelDetailDto addAmenitiesById(Long id, List<String> amenities);
}
