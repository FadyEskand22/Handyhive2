package com.handyHive23.handyHive23.service;

import java.time.LocalDateTime;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.provider.Provider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
    
@Entity
@Table(name = "service_like")
public class ServiceLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ServicePost service;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private com.handyHive23.handyHive23.provider.Provider provider;

    private LocalDateTime likedAt;

    // Getters and setters
    public Long getId() { return id; }

    public ServicePost getService() { return service; }
    public void setService(ServicePost service) { this.service = service; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Provider getProvider() { return provider; }
    public void setProvider(Provider provider) { this.provider = provider; }

    public LocalDateTime getLikedAt() { return likedAt; }
    public void setLikedAt(LocalDateTime likedAt) { this.likedAt = likedAt; }
}


