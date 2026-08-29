package com.vidilin.hotel.service;

import com.vidilin.hotel.Mapper.HotelMapper;
import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import com.vidilin.hotel.entity.Hotel;
import com.vidilin.hotel.repository.HotelRepository;
import com.vidilin.hotel.repository.HotelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
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
        List<Hotel> hotels = new ArrayList<>();
        Streamable.of(hotelRepository.findAll()).forEach(hotels::add);
        List<HotelSummaryDto> dtos = new ArrayList<>();
        for(Hotel hotel : hotels) {
            dtos.add(HotelMapper.mapToSummaryDto(hotel));
        }
        return dtos;
    }

    @Override
    public HotelDetailDto getHotelById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel.map(HotelMapper::mapToDetailDto).orElse(null);
    }

    @Override
    @Transactional
    public HotelSummaryDto saveHotel(SaveHotelDto dto) {
        Hotel hotel = HotelMapper.mapToEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return HotelMapper.mapToSummaryDto(savedHotel);
    }

    @Override
    @Transactional
    public void addAmenitiesById(Long id, List<String> amenities) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        hotel.ifPresent(value -> {
                    value.getAmenities().addAll(amenities);
                    hotelRepository.save(value);
                });
    }

    @Override
    public Map<String, Long> groupHotelsByParam(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        Map<String, Long> group;

        switch (param.toLowerCase()) {
            case "brand" -> group = hotels.stream()
                    .filter(h -> h.getBrand() != null)
                    .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));
            case "city" -> group = hotels.stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCity() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCity(), Collectors.counting()));
            case "country" -> group = hotels.stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCountry() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCountry(), Collectors.counting()));
            case "amenities" -> group = hotels.stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid group parameter: " + param);
        }

        return group;
    }

    @Override
    public List<HotelSummaryDto> searchHotels(String name, String brand, String city, String country, String amenities) {
        Specification<Hotel> spec = Specification.where(HotelSpecification.hasName(name))
                .and(HotelSpecification.hasBrand(brand))
                .and(HotelSpecification.hasCity(city))
                .and(HotelSpecification.hasCountry(country))
                .and(HotelSpecification.hasAmenity(amenities));

        List<Hotel> hotels = new ArrayList<>();
        Streamable.of(hotelRepository.findAll(spec)).forEach(hotels::add);
        List<HotelSummaryDto> dtos = new ArrayList<>();
        for(Hotel hotel : hotels) {
            dtos.add(HotelMapper.mapToSummaryDto(hotel));
        }
        return dtos;
    }
}
