package com.vidilin.hotel.service;

import com.vidilin.hotel.Mapper.HotelMapper;
import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.entity.Hotel;
import com.vidilin.hotel.repository.HotelRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelSummaryDto> getAllHotels() {
        List<HotelSummaryDto> hotels = new ArrayList<>();
        Streamable.of(hotelRepository.findAll()).toList();
        return hotels;
    }

    @Override
    public HotelDetailDto getHotelById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel.map(HotelMapper::mapToDetailDto).orElse(null);
    }

    @Override
    public HotelDetailDto createHotel(HotelDetailDto dto) {
        return null;
    }

    @Override
    public HotelDetailDto addAmenitiesById(Long id, List<String> amenities) {
        return null;
    }
}
