package com.vidilin.hotel.service;

import com.vidilin.hotel.dto.*;
import com.vidilin.hotel.enums.HotelParams;
import com.vidilin.hotel.exception.BadRequestException;
import com.vidilin.hotel.exception.NotFoundException;
import com.vidilin.hotel.mapper.HotelMapper;
import com.vidilin.hotel.repository.HotelRepository;
import com.vidilin.hotel.repository.HotelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelSummaryDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelMapper::mapToSummaryDto)
                .toList();
    }

    @Override
    public HotelDetailDto getHotelById(Long id) {
        return hotelRepository.findById(id).
                map(HotelMapper::mapToDetailDto).
                orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
    }

    @Override
    @Transactional
    public HotelSummaryDto saveHotel(SaveHotelDto dto) {
        return Optional.of(dto)
                .map(HotelMapper::mapToEntity)
                .map(hotelRepository::save)
                .map(HotelMapper::mapToSummaryDto)
                .orElseThrow(() -> new BadRequestException("Hotel not saved"));
    }

    @Override
    @Transactional
    public void addAmenitiesById(Long id, List<String> amenities) {
        var hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        hotel.getAmenities().addAll(amenities);
    }

    @Override
    public Map<String, Long> groupHotelsByParam(HotelParams param) {
        var results = switch (param) {
            case BRAND -> hotelRepository.countHotelsByBrand();
            case CITY -> hotelRepository.countHotelsByCity();
            case COUNTRY -> hotelRepository.countHotelsByCountry();
            case AMENITIES -> hotelRepository.countHotelsByAmenities();
        };
        return results.stream()
                .collect(Collectors.toMap(GroupCountDto::key, GroupCountDto::count));
    }

    @Override
    public List<HotelSummaryDto> searchHotels(HotelSearchRequest request) {
        if (request.isEmpty()) {
            throw new BadRequestException("At least one search parameter must be provided");
        }
        var spec = Specification.where(HotelSpecification.hasName(request.name()))
                .and(HotelSpecification.hasBrand(request.brand()))
                .and(HotelSpecification.hasCity(request.city()))
                .and(HotelSpecification.hasCountry(request.country()))
                .and(HotelSpecification.hasAmenities(request.amenities()));

        return hotelRepository.findAll(spec).stream()
                .map(HotelMapper::mapToSummaryDto)
                .toList();
    }
}