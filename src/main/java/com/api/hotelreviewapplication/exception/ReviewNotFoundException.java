package com.api.hotelreviewapplication.exception;

public class ReviewNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2;
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
