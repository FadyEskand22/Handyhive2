package com.handyHive23.handyHive23.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ProviderService, Long> {
    List<ProviderService> findByProviderId(Long providerId);

    @Query("SELECT s FROM ProviderService s WHERE LOWER(s.provider.expertise) LIKE LOWER(CONCAT('%', :expertise, '%'))")
    List<ProviderService> searchByExpertise(@Param("expertise") String expertise);
}