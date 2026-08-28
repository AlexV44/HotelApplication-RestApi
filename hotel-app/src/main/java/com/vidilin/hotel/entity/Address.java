package com.vidilin.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
