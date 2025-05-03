package com.handyHive23.handyHive23.review;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.service.ServicePost;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServicePost service;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setService(ServicePost service) {
        this.service = service;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}