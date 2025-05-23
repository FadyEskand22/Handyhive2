package com.handyHive23.handyHive23.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.savedProviders WHERE c.id = :id")
    Optional<Customer> findByIdWithSavedProviders(@Param("id") Long id);

}
