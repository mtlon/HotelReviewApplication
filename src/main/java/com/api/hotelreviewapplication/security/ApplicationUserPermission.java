package com.api.hotelreviewapplication.security;

public enum ApplicationUserPermission {
    HOTEL_READ("hotel: read"),
    HOTEL_CREATE("hotel: create"),
    HOTEL_UPDATE("hotel: update"),
    HOTEL_DELETE("hotel: delete"),
    REVIEW_READ("review: read"),
    REVIEW_CREATE("review: create"),
    REVIEW_UPDATE("review: update"),
    REVIEW_UPDATE_OWN("review: update own"),
    REVIEW_DELETE("review: delete"),
    REVIEW_DELETE_OWN("review: delete own");

    private final String permission;
    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
}
