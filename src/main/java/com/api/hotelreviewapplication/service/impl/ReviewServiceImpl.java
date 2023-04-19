package com.api.hotelreviewapplication.service.impl;

import com.api.hotelreviewapplication.dto.ReviewDto;
import com.api.hotelreviewapplication.exception.HotelNotFoundException;
import com.api.hotelreviewapplication.exception.ReviewNotFoundException;
import com.api.hotelreviewapplication.exception.UnauthorizedException;
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
    public ReviewServiceImpl(UserRepository userRepository,
                             HotelRepository hotelRepository,
                             ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    private Hotel getHotelById(int hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel was not found"));
    }
    private Review getReviewById(int reviewId, Hotel hotel) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review was not found"));

        if (!review.getHotel().equals(hotel)) {
            throw new ReviewNotFoundException("This review doesn't belong to this hotel");
        }
        return review;
    }
    public boolean isAdminOrModerator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")
                        || authority.getAuthority().equals("ROLE_MODERATOR"));
    }

    @Override
    public List<ReviewDto> getReviewByHotelId(int hotelId) {
        Hotel hotel = getHotelById(hotelId);

        List<Review> reviews = hotel.getReviews();
        return reviews.stream()
                .sorted(Comparator.comparingInt(Review::getId))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public ReviewDto getReviewById(int hotelId, int reviewId) {
        Hotel hotel = getHotelById(hotelId);
        Review review = getReviewById(reviewId, hotel);

        return mapToDto(review);
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, int hotelId) {
        Hotel hotel = getHotelById(hotelId);
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
        Hotel hotel = getHotelById(hotelId);
        Review review = getReviewById(reviewId, hotel);
        User currentUser = getCurrentUser();

        if (!(review.getUser().equals(currentUser) || isAdminOrModerator())) {
            throw new UnauthorizedException("You don't have permission to delete this review");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int hotelId, int reviewId) {
        Hotel hotel = getHotelById(hotelId);
        Review review = getReviewById(reviewId, hotel);
        User currentUser = getCurrentUser();

        if (!(review.getUser().equals(currentUser) || isAdminOrModerator())) {
            throw new UnauthorizedException("You don't have permission to delete this review");
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
}
