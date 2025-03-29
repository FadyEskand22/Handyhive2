package com.handyHive.handyhive.repository;

import com.handyHive.handyhive.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    Provider findByBusinessName(String businessName);
    List<Provider> findByAvailabilityAfter(Date date);
    boolean existsByBusinessName(String businessName);
} 