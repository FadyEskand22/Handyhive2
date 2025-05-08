package com.handyHive23.handyHive23.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceLikeRepository extends JpaRepository<ServiceLike, Long> {
    List<ServiceLike> findByService_Id(Long serviceId);
    int countByService_Id(Long serviceId);
}
