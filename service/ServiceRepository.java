package com.handyHive23.handyHive23.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServicePost, Long> {
    List<ServicePost> findByProviderId(Long providerId);

    @Query("SELECT s FROM ServicePost s WHERE LOWER(s.provider.expertise) LIKE LOWER(CONCAT('%', :expertise, '%'))")
    List<ServicePost> searchByExpertise(@Param("expertise") String expertise);
}