package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.HotelDto;

import java.util.List;

public interface HotelService {
    List<HotelDto> getAllHotels();
}