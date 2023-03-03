package com.api.hotelreviewapplication.exception;

public class HotelNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public HotelNotFoundException(String message) {
        super(message);
    }
}
