package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating a hotel")
public class SaveHotelDto {
    @Schema(description = "Hotel name")
    private String name;
    @Schema(description = "Hotel description")
    private String description;
    @Schema(description = "Hotel brand")
    private String brand;
    @Schema(description = "Hotel address details")
    private AddressDto address;
    @Schema(description = "Contact details")
    private ContactsDto contacts;
    @Schema(description = "Check-in and check-out times")
    private ArrivalTimeDto arrivalTime;

    public SaveHotelDto() {};

    public SaveHotelDto(String name, String description, String brand, AddressDto address, ContactsDto contacts, ArrivalTimeDto arrivalTime) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public ContactsDto getContacts() {
        return contacts;
    }

    public void setContacts(ContactsDto contacts) {
        this.contacts = contacts;
    }

    public ArrivalTimeDto getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTimeDto arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}