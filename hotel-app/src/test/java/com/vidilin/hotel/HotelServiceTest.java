package com.vidilin.hotel;

import com.vidilin.hotel.dto.HotelDetailDto;
import com.vidilin.hotel.dto.HotelSummaryDto;
import com.vidilin.hotel.dto.SaveHotelDto;
import com.vidilin.hotel.entity.Address;
import com.vidilin.hotel.entity.Hotel;
import com.vidilin.hotel.repository.HotelRepository;
import com.vidilin.hotel.service.HotelServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setCity("Minsk");
        address.setCountry("Belarus");

        hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setBrand("Hilton");
        hotel.setAddress(address);
        hotel.setAmenities(new ArrayList<>(List.of("Free WiFi", "Pool")));
    }

    @Test
    @DisplayName("getAllHotels — returning List<HotelSummaryDto>")
    void getAllHotels_ShouldReturnDtoList() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).getName());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getHotelById — returning hotel by id (positive)")
    void getHotelById_WhenHotelExists_ShouldReturnDetailDto() {
        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        HotelDetailDto result = hotelService.getHotelById(hotelId);

        assertNotNull(result);
        assertEquals("Hilton", result.getBrand());
        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("getHotelById — returning an EntityNotFoundException (negative)")
    void getHotelById_WhenHotelNotFound_ShouldReturnNull() {
        Long hotelId = 99L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> hotelService.getHotelById(hotelId));

        assertEquals("Hotel not found with id: 99", ex.getMessage());
        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("saveHotel — saving hotel and returning saved hotel")
    void saveHotel_ShouldSaveAndReturnSummaryDto() {
        SaveHotelDto dto = new SaveHotelDto();
        dto.setName("New hotel");

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelSummaryDto result = hotelService.saveHotel(dto);

        assertNotNull(result);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    @DisplayName("addAmenitiesById — adding amenities by hotel id (positive)")
    void addAmenitiesById_WhenHotelExists_ShouldAddAmenities() {
        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        List<String> newAmenities = List.of("Parking", "Gym");
        hotelService.addAmenitiesById(hotelId, newAmenities);

        assertEquals(4, hotel.getAmenities().size());
        assertTrue(hotel.getAmenities().contains("Parking"));
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    @DisplayName("addAmenitiesById — throwing an EntityNotFoundException, if hotel is not found (negative)")
    void addAmenitiesById_WhenNotFound_ShouldThrowException() {
        Long hotelId = 99L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> hotelService.addAmenitiesById(hotelId, List.of("Parking")));

        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by brand and returning Map<String, Long> (positive)")
    void groupHotelsByParam_ByBrand_ShouldReturnGroupedMap() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        Map<String, Long> result = hotelService.groupHotelsByParam("brand");

        assertNotNull(result);
        assertEquals(1L, result.get("Hilton"));
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by city and returning Map<String, Long> (positive)")
    void groupHotelsByParam_ByCity_ShouldReturnGroupedMap() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        Map<String, Long> result = hotelService.groupHotelsByParam("city");

        assertNotNull(result);
        assertEquals(1L, result.get("Minsk"));
    }

    @Test
    @DisplayName("groupHotelsByParam — throwing a BadRequestException because of invalid parameter")
    void groupHotelsByParam_InvalidParam_ShouldThrowException() {
        assertThrows(ResponseStatusException.class, () ->
                hotelService.groupHotelsByParam("invalid_param")
        );
    }

    @Test
    @DisplayName("searchHotels — searching with specification and returning result")
    void searchHotels_ShouldReturnMatchingHotels() {
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels("Test", "Hilton", "Minsk", "Belarus", "Free WiFi");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }
}