package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.ReviewDto;
import com.api.hotelreviewapplication.exception.HotelNotFoundException;
import com.api.hotelreviewapplication.exception.ReviewNotFoundException;
import com.api.hotelreviewapplication.model.Hotel;
import com.api.hotelreviewapplication.model.Review;
import com.api.hotelreviewapplication.model.User;
import com.api.hotelreviewapplication.repository.HotelRepository;
import com.api.hotelreviewapplication.repository.ReviewRepository;
import com.api.hotelreviewapplication.repository.UserRepository;
import com.api.hotelreviewapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private ReviewRepository reviewRepository;
    @Autowired
    public ReviewServiceImpl(HotelRepository hotelRepository,
                             UserRepository userRepository,
                             ReviewRepository reviewRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
    public ReviewDto getReviewById(int hotelID, int reviewID) {
    Hotel hotel = hotelRepository.findById(hotelID)
            .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

    Review review = reviewRepository.findById(reviewID)
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
        review.setUser(getCurrentUser());

        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
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
        User currentUser = getCurrentUser();
        if (!review.getUser().equals(currentUser)) {
            System.out.println("You don't have permission to update this review");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int hotelId, int reviewId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new HotelNotFoundException("Hotel was not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review was not found"));

        if (!review.getHotel().equals(hotel)) {
            throw new ReviewNotFoundException("This review doesn't belong to this hotel");
        }

        User currentUser = getCurrentUser();
        if (!review.getUser().equals(currentUser)) {
            System.out.println("You don't have permission to update this review");
        }

        reviewRepository.deleteById(review.getId());
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
