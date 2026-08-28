package com.vidilin.hotel.dto;

import com.vidilin.hotel.entity.Contacts;

public class ContactsDto {
    private String phone;
    private String email;

    public ContactsDto() {};

    public ContactsDto(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
