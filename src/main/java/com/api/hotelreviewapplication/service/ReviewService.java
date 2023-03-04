package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.ReviewDto;

public interface ReviewService {
    ReviewDto createReview (ReviewDto reviewDto, int hotelId);
}
