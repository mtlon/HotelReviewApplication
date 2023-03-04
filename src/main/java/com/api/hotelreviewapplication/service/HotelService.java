package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.model.Hotel;

import java.util.List;

public interface HotelService {
    List<HotelDto> getAllHotels();
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto updateHotel(HotelDto hotelDto, int id);
}