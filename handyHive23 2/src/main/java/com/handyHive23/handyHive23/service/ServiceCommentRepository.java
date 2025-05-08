package com.handyHive23.handyHive23.service;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCommentRepository extends JpaRepository<ServiceComment, Long> {
    List<ServiceComment> findByService_Id(Long serviceId);
    int countByService_Id(Long serviceId);
}
