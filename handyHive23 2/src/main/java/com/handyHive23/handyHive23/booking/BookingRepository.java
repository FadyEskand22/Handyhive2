package com.handyHive23.handyHive23.booking;
 
 import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
 public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByProviderId(Long providerId);
 }