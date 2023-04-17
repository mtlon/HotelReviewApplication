package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.dto.HotelResponseDto;

public interface HotelService {
    HotelResponseDto getAllHotels(int pageNo, int pageSize);
    HotelDto getHotelByID(int id);
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto updateHotel(HotelDto hotelDto, int id);
    void deleteHotel(int id);
}