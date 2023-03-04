package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.ReviewDto;
import com.api.hotelreviewapplication.exception.HotelNotFoundException;
import com.api.hotelreviewapplication.model.Hotel;
import com.api.hotelreviewapplication.model.Review;
import com.api.hotelreviewapplication.repository.HotelRepository;
import com.api.hotelreviewapplication.repository.ReviewRepository;
import com.api.hotelreviewapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private HotelRepository hotelRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(HotelRepository hotelRepository, ReviewRepository reviewRepository) {
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    public ReviewDto createReview(ReviewDto reviewDto, int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setHotel(hotel);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview );
    }
    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
