package com.api.hotelreviewapplication.exception;

public class JwtAuthenticationException extends RuntimeException{
    private static final long serialVersionUID = 1;
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
