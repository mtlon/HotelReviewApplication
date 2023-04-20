package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.dto.HotelResponseDto;
import com.api.hotelreviewapplication.exception.HotelNotFoundException;
import com.api.hotelreviewapplication.exception.UnauthorizedException;
import com.api.hotelreviewapplication.model.Hotel;
import com.api.hotelreviewapplication.repository.HotelRepository;
import com.api.hotelreviewapplication.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;
    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    @Override
    public HotelResponseDto getAllHotels(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        Page<Hotel> hotels = hotelRepository.findAll(pageable);

        List<Hotel> listOfHotels = hotels.getContent();
        List<HotelDto> content = listOfHotels.stream().map(this::mapToDto).collect(Collectors.toList());

        HotelResponseDto hotelResponse = new HotelResponseDto();
        hotelResponse.setContent(content);
        hotelResponse.setPageNo(hotels.getNumber());
        hotelResponse.setPageSize(hotels.getSize());
        hotelResponse.setTotalElements(hotels.getTotalElements());
        hotelResponse.setTotalPages(hotels.getTotalPages());
        hotelResponse.setLast(hotels.isLast());

        return hotelResponse;
    }
    @Override
    public HotelDto getHotelByID(int id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            throw new UnauthorizedException("Hotel not found");
        }
        Hotel hotel = optionalHotel.get();
        return mapToDto(hotel);
    }
    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setCity(hotelDto.getCity());
        hotel.setNumberOfRooms(hotelDto.getNumberOfRooms());

        Hotel newHotel = hotelRepository.save(hotel);
        hotelDto.setId(newHotel.getId());

        return hotelDto;
    }
    @Override
    public HotelDto updateHotel(HotelDto hotelDto, int id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        hotel.setName(hotelDto.getName());
        hotel.setCity(hotelDto.getCity());
        hotel.setNumberOfRooms(hotelDto.getNumberOfRooms());

        hotelRepository.save(hotel);
        return mapToDto(hotel);
    }
    @Override
    public void deleteHotel(int id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        hotelRepository.delete(hotel);
    }
    private HotelDto mapToDto(Hotel hotel) {
        HotelDto hotelDTO = new HotelDto();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setCity(hotel.getCity());
        hotelDTO.setNumberOfRooms(hotel.getNumberOfRooms());
        return hotelDTO;
    }
}