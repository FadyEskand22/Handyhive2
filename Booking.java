package com.handyHive.handyhive.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "providerId", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "seviceId", nullable = false)
    private Service service;

    @Column(name = "bookingDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
} 