package com.handyHive23.handyHive23.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{customerId}/service/{serviceId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long customerId,
            @PathVariable Long serviceId,
            @RequestBody Review review
    ) {
        return ResponseEntity.ok(reviewService.createReview(customerId, serviceId, review));
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Review>> getReviewsForService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(reviewService.getReviewsForService(serviceId));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Review>> getReviewsByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(reviewService.getReviewsByProvider(providerId));
    }

    @GetMapping("/provider/{providerId}/stats")
    public ResponseEntity<List<ServiceReviewStats>> getReviewStatsForProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(reviewService.getReviewStatsByProvider(providerId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }


}
