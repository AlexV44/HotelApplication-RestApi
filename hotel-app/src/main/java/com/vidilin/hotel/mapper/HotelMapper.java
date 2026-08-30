package com.vidilin.hotel.mapper;

import com.vidilin.hotel.dto.*;
import com.vidilin.hotel.entity.Address;
import com.vidilin.hotel.entity.ArrivalTime;
import com.vidilin.hotel.entity.Contacts;
import com.vidilin.hotel.entity.Hotel;

import java.util.Optional;

public class HotelMapper {
    public static HotelSummaryDto mapToSummaryDto(Hotel hotel) {
        return Optional.ofNullable(hotel)
                .map(h -> new HotelSummaryDto(
                        h.getId(),
                        h.getName(),
                        h.getDescription(),
                        mapToAddressDto(h.getAddress()).toString(),
                        Optional.ofNullable(h.getContacts()).map(Contacts::getPhone).orElse(null)
                ))
                .orElse(null);
    }

    public static HotelDetailDto mapToDetailDto(Hotel hotel) {
        return Optional.ofNullable(hotel)
                .map(h -> new HotelDetailDto(
                        h.getId(),
                        h.getName(),
                        h.getDescription(),
                        h.getBrand(),
                        mapToAddressDto(h.getAddress()),
                        mapToContactsDto(h.getContacts()),
                        mapToArrivalTimeDto(h.getArrivalTime()),
                        h.getAmenities()
                ))
                .orElse(null);
    }

    public static Hotel mapToEntity(SaveHotelDto dto) {
        if (dto == null) {
            return null;
        }

        var hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setBrand(dto.getBrand());
        hotel.setAddress(mapToAddressEntity(dto.getAddress()));
        hotel.setContacts(mapToContactsEntity(dto.getContacts()));
        hotel.setArrivalTime(mapToArrivalTimeEntity(dto.getArrivalTime()));

        return hotel;
    }

    private static AddressDto mapToAddressDto(Address address) {
        return Optional.ofNullable(address)
                .map(a -> new AddressDto(a.getHouseNumber(), a.getStreet(), a.getCity(), a.getCountry(), a.getPostCode()))
                .orElse(null);
    }

    private static ContactsDto mapToContactsDto(Contacts contacts) {
        return Optional.ofNullable(contacts)
                .map(c -> new ContactsDto(c.getPhone(), c.getEmail()))
                .orElse(null);
    }

    private static ArrivalTimeDto mapToArrivalTimeDto(ArrivalTime arrivalTime) {
        return Optional.ofNullable(arrivalTime)
                .map(at -> new ArrivalTimeDto(at.getCheckIn(), at.getCheckOut()))
                .orElse(null);
    }

    private static Address mapToAddressEntity(AddressDto dto) {
        return Optional.ofNullable(dto).
                map(aDto -> new Address(aDto.houseNumber(), aDto.street(), aDto.city(), aDto.country(), aDto.postCode()))
                .orElse(null);
    }

    private static Contacts mapToContactsEntity(ContactsDto dto) {
        return Optional.ofNullable(dto).
                map(cDto -> new Contacts(cDto.phone(), cDto.email()))
                .orElse(null);
    }

    private static ArrivalTime mapToArrivalTimeEntity(ArrivalTimeDto dto) {
        return Optional.ofNullable(dto).
                map(atDto -> new ArrivalTime(atDto.checkIn(), atDto.checkOut()))
                .orElse(null);
    }
}