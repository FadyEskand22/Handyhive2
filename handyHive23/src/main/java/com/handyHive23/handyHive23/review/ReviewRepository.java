package com.handyHive23.handyHive23.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceId(Long serviceId);
    List<Review> findByServiceProviderId(Long providerId);

    @Query(value = "SELECT s.service_id, s.service_title, s.average_rating, s.review_count " +
               "FROM provider_review_stats s WHERE s.provider_id = :providerId", nativeQuery = true)
    List<Object[]> getStatsByProviderId(Long providerId);


}
