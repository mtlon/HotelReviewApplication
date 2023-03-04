package com.api.hotelreviewapplication.controller;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.dto.HotelResponse;
import com.api.hotelreviewapplication.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HotelController {
    private HotelService hotelService;
    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @GetMapping("/hotels")
    private ResponseEntity<HotelResponse> getAllHotels(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize) {
        HotelResponse hotelResponse = hotelService.getAllHotels(pageNo, pageSize);
        return ResponseEntity.ok(hotelResponse);
    }
    @PostMapping("/hotel/create")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        return new ResponseEntity<>(hotelService.createHotel(hotelDto), HttpStatus.CREATED);
    }
    @PutMapping("/hotel/update/{id}")
    private ResponseEntity<HotelDto> updateHotel(@RequestBody HotelDto hotelDto, @PathVariable("id") int id) {
        HotelDto updatedHotel = hotelService.updateHotel(hotelDto, id);
        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }
    @DeleteMapping("/hotel/delete/{id}")
    private ResponseEntity<String> deleteHotel(@PathVariable("id") int id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
    }
}