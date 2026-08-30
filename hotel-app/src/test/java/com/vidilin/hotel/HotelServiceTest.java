package com.vidilin.hotel;

import com.vidilin.hotel.dto.*;
import com.vidilin.hotel.entity.Address;
import com.vidilin.hotel.entity.Hotel;
import com.vidilin.hotel.enums.HotelParams;
import com.vidilin.hotel.exception.BadRequestException;
import com.vidilin.hotel.exception.NotFoundException;
import com.vidilin.hotel.repository.HotelRepository;
import com.vidilin.hotel.service.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

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
        var address = new Address();
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

        var result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).name());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getHotelById — returning hotel by id (positive)")
    void getHotelById_WhenHotelExists_ShouldReturnDetailDto() {
        var hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        var result = hotelService.getHotelById(hotelId);

        assertNotNull(result);
        assertEquals("Hilton", result.brand());
        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("getHotelById — returning NotFoundException (negative)")
    void getHotelById_WhenHotelNotFound_ShouldReturnNull() {
        var hotelId = 99L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        var ex = assertThrows(NotFoundException.class,
                () -> hotelService.getHotelById(hotelId));

        assertEquals("Hotel not found with id: 99", ex.getMessage());
        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("saveHotel — saving hotel and returning saved hotel")
    void saveHotel_ShouldSaveAndReturnSummaryDto() {
        var dto = new SaveHotelDto();
        dto.setName("New hotel");

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        var result = hotelService.saveHotel(dto);

        assertNotNull(result);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    @DisplayName("addAmenitiesById — adding amenities by hotel id (positive)")
    void addAmenitiesById_WhenHotelExists_ShouldAddAmenities() {
        var hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAmenities(new ArrayList<>(List.of("WiFi")));

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        hotelService.addAmenitiesById(1L, List.of("Pool"));

        verify(hotelRepository).findById(1L);

        assertTrue(hotel.getAmenities().containsAll(List.of("WiFi", "Pool")));
    }

    @Test
    @DisplayName("addAmenitiesById — throwing NotFoundException, if hotel is not found (negative)")
    void addAmenitiesById_WhenNotFound_ShouldThrowException() {
        var hotelId = 99L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> hotelService.addAmenitiesById(hotelId, List.of("Parking")));

        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by brand (positive)")
    void groupHotelsByParam_ByBrand_ShouldReturnGroupedMap() {
        var mockData = List.of(new GroupCountDto("Hilton", 1L));
        when(hotelRepository.countHotelsByBrand()).thenReturn(mockData);

        Map<String, Long> result = hotelService.groupHotelsByParam(HotelParams.BRAND);

        assertNotNull(result);
        assertEquals(1L, result.get("Hilton"));
        verify(hotelRepository).countHotelsByBrand();
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by city (positive)")
    void groupHotelsByParam_ByCity_ShouldReturnGroupedMap() {
        var mockGroupData = List.of(new GroupCountDto("Minsk", 1L));
        when(hotelRepository.countHotelsByCity()).thenReturn(mockGroupData);

        Map<String, Long> result = hotelService.groupHotelsByParam(HotelParams.CITY);

        assertNotNull(result);
        assertEquals(1L, result.get("Minsk"));
        verify(hotelRepository).countHotelsByCity();
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by country (positive)")
    void groupHotelsByParam_ByCountry_ShouldReturnGroupedMap() {
        var mockData = List.of(new GroupCountDto("Belarus", 1L));
        when(hotelRepository.countHotelsByCountry()).thenReturn(mockData);

        Map<String, Long> result = hotelService.groupHotelsByParam(HotelParams.COUNTRY);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get("Belarus"));
        verify(hotelRepository, times(1)).countHotelsByCountry();
    }

    @Test
    @DisplayName("groupHotelsByParam — grouping by amenities (positive)")
    void groupHotelsByParam_ByAmenities_ShouldReturnGroupedMap() {
        var mockData = List.of(
                new GroupCountDto("Free WiFi", 1L),
                new GroupCountDto("Pool", 1L)
        );
        when(hotelRepository.countHotelsByAmenities()).thenReturn(mockData);

        Map<String, Long> result = hotelService.groupHotelsByParam(HotelParams.AMENITIES);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get("Free WiFi"));
        assertEquals(1L, result.get("Pool"));
        verify(hotelRepository, times(1)).countHotelsByAmenities();
    }

    @Test
    @DisplayName("groupHotelsByParam — throwing a BadRequestException because of invalid parameter (negative)")
    void groupHotelsByParam_InvalidParam_ShouldThrowException() {
        assertThrows(BadRequestException.class, () ->
                hotelService.groupHotelsByParam(HotelParams.fromString("invalid_param"))
        );
    }

    @Test
    @DisplayName("searchHotels — searching with specification and returning result")
    void searchHotels_ShouldReturnMatchingHotels() {
        var searchRequest = new HotelSearchRequest("Test Hotel", "Hilton", "Minsk", "Belarus", List.of("Free WiFi"));

        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        var result = hotelService.searchHotels(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — search by name (positive)")
    void searchHotels_ByName_ShouldReturnMatchingHotels() {
        var request = new HotelSearchRequest("Test Hotel", null, null, null, null);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).name());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — search by brand (positive)")
    void searchHotels_ByBrand_ShouldReturnMatchingHotels() {
        var request = new HotelSearchRequest(null, "Hilton", null, null, null);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Hotel", result.get(0).name());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — search by city (positive)")
    void searchHotels_ByCity_ShouldReturnMatchingHotels() {
        var request = new HotelSearchRequest(null, null, "Minsk", null, null);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — search by country (positive)")
    void searchHotels_ByCountry_ShouldReturnMatchingHotels() {
        var request = new HotelSearchRequest(null, null, null, "Belarus", null);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — search by amenities (positive)")
    void searchHotels_ByAmenities_ShouldReturnMatchingHotels() {
        var request = new HotelSearchRequest(null, null, null, null, List.of("Pool"));
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));

        List<HotelSummaryDto> result = hotelService.searchHotels(request);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("searchHotels — throwing BadRequestException when no search parameters are provided (negative)")
    void searchHotels_WhenNoParametersProvided_ShouldThrowBadRequestException() {
        var emptyRequest = new HotelSearchRequest(null, null, null, null, null);

        var exception = assertThrows(BadRequestException.class,
                () -> hotelService.searchHotels(emptyRequest));

        assertEquals("At least one search parameter must be provided", exception.getMessage());
        verify(hotelRepository, never()).findAll(any(Specification.class));
    }
}