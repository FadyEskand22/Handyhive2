package com.handyHive23.handyHive23.provider;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    @Query("SELECT p FROM Provider p LEFT JOIN FETCH p.savedByUsers WHERE p.id = :id")
    Optional<Provider> findByIdWithSavedUsers(@Param("id") Long id);

    List<Provider> findByExpertiseContainingIgnoreCase(String keyword);

    List<Provider> findByExpertiseContainingIgnoreCaseOrBusinessNameContainingIgnoreCase(String expertise, String businessName);

    @Query("SELECT p FROM Provider p WHERE LOWER(p.expertise) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.businessName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Provider> searchByKeyword(@Param("keyword") String keyword);




}
