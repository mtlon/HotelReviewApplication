package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.ReviewDto;

import com.api.hotelreviewapplication.exception.HotelNotFoundException;
import com.api.hotelreviewapplication.exception.ReviewNotFoundException;
import com.api.hotelreviewapplication.model.Hotel;
import com.api.hotelreviewapplication.model.Review;
import com.api.hotelreviewapplication.repository.HotelRepository;
import com.api.hotelreviewapplication.repository.ReviewRepository;
import com.api.hotelreviewapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ReviewDto> getReviewByHotelId(int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        List<Review> reviews = hotel.getReviews();
        return reviews.stream()
                .sorted(Comparator.comparingInt(Review::getStars).reversed())
                .map(review -> mapToDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int hotelId, int reviewId) {
    Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

    Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException("Review was not found"));

      if (!review.getHotel().equals(hotel)) {
          throw new ReviewNotFoundException("This review doesn't belong to this hotel");
      }

      return mapToDto(review);
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

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto, int hotelId, int reviewId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review was not found"));

        if (!review.getHotel().equals(hotel)) {
            throw new ReviewNotFoundException("Review with ID " + reviewId + " doesn't belong to hotel with ID " + hotelId);
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
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
