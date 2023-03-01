package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.model.Hotel;
import com.api.hotelreviewapplication.repository.HotelRepository;
import com.api.hotelreviewapplication.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    
    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }
    private HotelDto mapToDto(Hotel hotel) {
        HotelDto hotelDTO = new HotelDto();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setCity(hotel.getCity());
        hotelDTO.setNumberOfRooms(hotel.getNumberOfRooms());
        return hotelDTO;
    }
    private Hotel mapToEntity(HotelDto hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getId());
        hotel.setName(hotelDTO.getName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setNumberOfRooms(hotelDTO.getNumberOfRooms());
        return hotel;
    }
}
