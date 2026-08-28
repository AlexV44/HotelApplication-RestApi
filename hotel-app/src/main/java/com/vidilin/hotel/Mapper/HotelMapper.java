package com.vidilin.hotel.Mapper;

import com.vidilin.hotel.dto.*;
import com.vidilin.hotel.entity.Address;
import com.vidilin.hotel.entity.ArrivalTime;
import com.vidilin.hotel.entity.Contacts;
import com.vidilin.hotel.entity.Hotel;

import java.util.ArrayList;

public class HotelMapper {
    public static HotelSummaryDto mapToSummaryDto(Hotel hotel) {
        if(hotel == null) {
            return null;
        }
        HotelSummaryDto dto = new HotelSummaryDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setAddress(hotel.getAddress().toString());
        dto.setPhone(hotel.getContacts() != null ? hotel.getContacts().getPhone() : null);
        return dto;
    }

    public static HotelDetailDto mapToDetailDto(Hotel hotel) {
        if(hotel == null) {
            return null;
        }
        HotelDetailDto dto = new HotelDetailDto();

        AddressDto addressDto = null;
        if (hotel.getAddress() != null) {
            Address address = hotel.getAddress();
            addressDto = new AddressDto(
                    address.getHouseNumber(),
                    address.getStreet(),
                    address.getCity(),
                    address.getCountry(),
                    address.getPostCode()
            );
        }

        ContactsDto contactsDto = null;
        if (hotel.getContacts() != null) {
            Contacts contacts = hotel.getContacts();
            contactsDto = new ContactsDto(
                    contacts.getPhone(),
                    contacts.getEmail()
            );
        }

        ArrivalTimeDto arrivalTimeDto = null;
        if (hotel.getArrivalTime() != null) {
            ArrivalTime at = hotel.getArrivalTime();
            arrivalTimeDto = new ArrivalTimeDto(
                    at.getCheckIn(),
                    at.getCheckOut()
            );
        }

        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setBrand(hotel.getBrand());
        dto.setAddress(addressDto);
        dto.setContacts(contactsDto);
        dto.setArrivalTime(arrivalTimeDto);
        dto.setAmenities(hotel.getAmenities() != null ? new ArrayList<>(hotel.getAmenities()) : new ArrayList<>());

        return dto;
    }
}