package com.handyHive.handyhive.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId")
    private Integer serviceId;

    @ManyToOne
    @JoinColumn(name = "providerId", nullable = false)
    private Provider provider;

    @Column(name = "serviceName", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "filter", nullable = false, length = 50)
    private String filter;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "availability", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date availability;

    @OneToMany(mappedBy = "service")
    private List<Booking> bookings;
} 