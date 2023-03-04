package com.api.hotelreviewapplication.controller;

import com.api.hotelreviewapplication.dto.ReviewDto;
import com.api.hotelreviewapplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/hotel/{id}")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, @PathVariable("id") int hotelId) {
        return new ResponseEntity<>(reviewService.createReview(reviewDto, hotelId), HttpStatus.CREATED);
    }
}
