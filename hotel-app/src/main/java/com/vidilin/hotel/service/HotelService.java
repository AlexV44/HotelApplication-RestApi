package com.vidilin.hotel.service;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSearchRequest;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import com.vidilin.hotel.enums.HotelParams;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelSummaryDto> getAllHotels();
    HotelDetailDto getHotelById(Long id);
    HotelSummaryDto saveHotel(SaveHotelDto dto);
    void addAmenitiesById(Long id, List<String> amenities);
    Map<String, Long> groupHotelsByParam(HotelParams param);
    List<HotelSummaryDto> searchHotels(HotelSearchRequest request);
}
