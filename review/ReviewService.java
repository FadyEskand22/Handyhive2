package com.handyHive23.handyHive23.review;

import com.handyHive23.handyHive23.customer.CustomerRepository;
import com.handyHive23.handyHive23.service.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository, ServiceRepository serviceRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }

    public Review createReview(Long customerId, Long serviceId, Review review) {
        var customer = customerRepository.findById(customerId).orElseThrow();
        var service = serviceRepository.findById(serviceId).orElseThrow();
        review.setCustomer(customer);
        review.setService(service);
        review.setReviewDate(new java.sql.Date(System.currentTimeMillis()));
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForService(Long serviceId) {
        return reviewRepository.findByServiceId(serviceId);
    }

    public List<Review> getReviewsByProvider(Long providerId) {
        return reviewRepository.findByServiceProviderId(providerId);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}