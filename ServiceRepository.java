package com.handyHive.handyhive.repository;

import com.handyHive.handyhive.model.Service;
import com.handyHive.handyhive.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findByProvider(Provider provider);
    List<Service> findByFilter(String filter);
    List<Service> findByPriceLessThanEqual(Integer maxPrice);
    List<Service> findByAvailabilityAfter(Date date);
} 