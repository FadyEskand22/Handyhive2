package com.handyHive23.handyHive23.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceReviewStats {
    private Long serviceId;
    private String serviceTitle;
    private Double averageRating;
    private Long reviewCount;
}
