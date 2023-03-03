package com.api.hotelreviewapplication.dto;

import lombok.Data;

@Data
public class HotelDto {
    private int id;
    private String name;
    private String city;
    private int numberOfRooms;
}