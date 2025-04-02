package com.handyHive.handyhive.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "providerId")
    private Integer providerId;

    @Column(name = "businessName", nullable = false, length = 250)
    private String businessName;

    @Column(name = "serviceDescription", nullable = false)
    private String serviceDescription;

    @Column(name = "price", nullable = false, length = 100)
    private String price;

    @Column(name = "availability", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date availability;
} 