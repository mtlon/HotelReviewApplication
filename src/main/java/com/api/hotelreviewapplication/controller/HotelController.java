package com.api.hotelreviewapplication.controller;

import com.api.hotelreviewapplication.dto.HotelDto;
import com.api.hotelreviewapplication.dto.HotelResponseDto;
import com.api.hotelreviewapplication.service.HotelService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<HotelResponseDto> getAllHotels(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        HotelResponseDto hotelResponse = hotelService.getAllHotels(pageNo, pageSize);
        return ResponseEntity.ok(hotelResponse);
    }
    @GetMapping("/hotel/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<HotelDto> getHotelByID(@PathVariable("id") int id) {
        HotelDto getHotelById = hotelService.getHotelByID(id);
        return new ResponseEntity<>(getHotelById, HttpStatus.OK);
    }
    @PostMapping("/hotel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        return new ResponseEntity<>(hotelService.createHotel(hotelDto), HttpStatus.CREATED);
    }
    @PutMapping("/hotel/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<HotelDto> updateHotel(@RequestBody HotelDto hotelDto, @PathVariable("id") int id) {
        HotelDto updatedHotel = hotelService.updateHotel(hotelDto, id);
        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }
    @DeleteMapping("/hotel/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteHotel(@PathVariable("id") int id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
    }
}