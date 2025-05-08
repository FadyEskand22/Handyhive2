package com.handyHive23.handyHive23.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.provider.Provider;

import jakarta.persistence.*;

@Entity
@Table(name = "service_comment")
public class ServiceComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ServicePost service;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private com.handyHive23.handyHive23.provider.Provider provider;

    private String content;
    private LocalDateTime commentedAt;

    // Getters and setters
    public Long getId() { return id; }

    public ServicePost getService() { return service; }
    public void setService(ServicePost service) { this.service = service; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Provider getProvider() { return provider; }
    public void setProvider(Provider provider) { this.provider = provider; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCommentedAt() { return commentedAt; }
    public void setCommentedAt(LocalDateTime commentedAt) { this.commentedAt = commentedAt; }
}
