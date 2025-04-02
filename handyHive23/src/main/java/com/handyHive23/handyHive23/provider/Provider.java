package com.handyHive23.handyHive23.provider;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String expertise;
    private String availability;
    private String pricingDetails;
}
