package com.vidilin.hotel.service;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelSummaryDto> getAllHotels();
    HotelDetailDto getHotelById(Long id);
    HotelSummaryDto saveHotel(SaveHotelDto dto);
    void addAmenitiesById(Long id, List<String> amenities);
    Map<String, Long> groupHotelsByParam(String param);
    List<HotelSummaryDto> searchHotels(String name, String brand, String city, String country, String amenities);
}
