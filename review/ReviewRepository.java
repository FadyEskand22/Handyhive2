package com.handyHive23.handyHive23.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceId(Long serviceId);

    @Query("SELECT r FROM Review r WHERE r.service.provider.id = :providerId")
    List<Review> findByServiceProviderId(Long providerId);
}