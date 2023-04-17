package com.api.hotelreviewapplication.controller;

import com.api.hotelreviewapplication.dto.ReviewDto;
import com.api.hotelreviewapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping("hotel/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewByHotelId(@PathVariable("id") int hotelId) {
        List<ReviewDto> reviewList = reviewService.getReviewByHotelId(hotelId);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }
    @GetMapping("hotel/{id}/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("id") int hotelId, @PathVariable("reviewId") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(hotelId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }
    @PostMapping("/hotel/{id}/review")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, @PathVariable("id") int hotelId) {
        ReviewDto review = reviewService.createReview(reviewDto, hotelId);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @PutMapping("hotel/{id}/review/{reviewId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto reviewDto, @PathVariable("id") int id, @PathVariable("reviewId") int reviewId) {
        ReviewDto review = reviewService.updateReview(reviewDto, id, reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @DeleteMapping("hotel/{id}/review/{reviewId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public ResponseEntity<String> deleteReviewById(@PathVariable("id") int hotelId, @PathVariable("reviewId") int reviewId) {
        reviewService.deleteReview(hotelId, reviewId);
        return new ResponseEntity<>("Review with ID " + reviewId + " deleted successfully.", HttpStatus.OK);
    }
}
