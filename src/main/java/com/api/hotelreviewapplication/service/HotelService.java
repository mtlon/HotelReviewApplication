package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.dto.HotelResponse;

public interface HotelService {
    HotelResponse getAllHotels(int pageNo, int pageSize);
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto updateHotel(HotelDto hotelDto, int id);
    void deleteHotel(int id);
}