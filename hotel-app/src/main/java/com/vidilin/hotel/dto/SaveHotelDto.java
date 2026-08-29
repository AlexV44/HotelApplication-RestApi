package com.vidilin.hotel.dto;

public class SaveHotelDto {
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;

    public SaveHotelDto() {
    }

    public SaveHotelDto(String name, String description, String brand,
                          AddressDto address, ContactsDto contacts, ArrivalTimeDto arrivalTime) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public AddressDto getAddress() { return address; }
    public void setAddress(AddressDto address) { this.address = address; }

    public ContactsDto getContacts() { return contacts; }
    public void setContacts(ContactsDto contacts) { this.contacts = contacts; }

    public ArrivalTimeDto getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(ArrivalTimeDto arrivalTime) { this.arrivalTime = arrivalTime; }
}
