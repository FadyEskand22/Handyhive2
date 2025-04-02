package com.handyHive23.handyHive23.review;

import com.handyHive23.handyHive23.customer.CustomerRepository;
import com.handyHive23.handyHive23.service.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForService(Long serviceId) {
        return reviewRepository.findByServiceId(serviceId);
    }

    public List<Review> getReviewsByProvider(Long providerId) {
        return reviewRepository.findByServiceProviderId(providerId);
    }

    public List<ServiceReviewStats> getReviewStatsByProvider(Long providerId) {
    List<Object[]> raw = reviewRepository.getStatsByProviderId(providerId);
    List<ServiceReviewStats> result = new ArrayList<>();

    for (Object[] row : raw) {
        result.add(new ServiceReviewStats(
            ((Number) row[0]).longValue(),           // service_id
            (String) row[1],                         // service_title
            ((Number) row[2]).doubleValue(),         // average_rating
            ((Number) row[3]).longValue()            // review_count
        ));
    }

    return result;
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
    
    
}
