package com.vidilin.hotel.repository;

import com.vidilin.hotel.dto.GroupCountDto;
import com.vidilin.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    @Query("""
        SELECT new com.vidilin.hotel.dto.GroupCountDto(h.brand, COUNT(h)) 
        FROM Hotel h 
        WHERE h.brand IS NOT NULL 
        GROUP BY h.brand
    """)
    List<GroupCountDto> countHotelsByBrand();

    @Query("""
        SELECT new com.vidilin.hotel.dto.GroupCountDto(h.address.city, COUNT(h)) 
        FROM Hotel h 
        WHERE h.address IS NOT NULL AND h.address.city IS NOT NULL 
        GROUP BY h.address.city
    """)
    List<GroupCountDto> countHotelsByCity();

    @Query("""
        SELECT new com.vidilin.hotel.dto.GroupCountDto(h.address.country, COUNT(h)) 
        FROM Hotel h 
        WHERE h.address IS NOT NULL AND h.address.country IS NOT NULL 
        GROUP BY h.address.country
    """)
    List<GroupCountDto> countHotelsByCountry();

    @Query("""
        SELECT new com.vidilin.hotel.dto.GroupCountDto(a, COUNT(h)) 
        FROM Hotel h 
        JOIN h.amenities a 
        GROUP BY a
    """)
    List<GroupCountDto> countHotelsByAmenities();
}
