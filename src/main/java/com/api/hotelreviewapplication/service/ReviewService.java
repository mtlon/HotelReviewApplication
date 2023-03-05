package com.api.hotelreviewapplication.service;

import com.api.hotelreviewapplication.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewByHotelId(int hotelId);
    ReviewDto getReviewById(int hotelId, int reviewId);
    ReviewDto createReview (ReviewDto reviewDto, int hotelId);
    ReviewDto updateReview(ReviewDto reviewDto, int hotelId, int reviewId);
    void deleteReview (int hotelId, int reviewId);


}
