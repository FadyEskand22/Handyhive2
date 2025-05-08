package com.handyHive23.handyHive23.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handyHive23.handyHive23.booking.Booking;
import com.handyHive23.handyHive23.booking.BookingRepository;
import com.handyHive23.handyHive23.review.Review;
import com.handyHive23.handyHive23.review.ReviewDTO;
import com.handyHive23.handyHive23.review.ReviewRepository;
import com.handyHive23.handyHive23.service.ServiceCommentRepository;
import com.handyHive23.handyHive23.service.ServiceLikeRepository;
import com.handyHive23.handyHive23.service.ServicePost;
import com.handyHive23.handyHive23.service.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    private final ReviewRepository reviewRepository;

public ProviderService(ProviderRepository providerRepository, ReviewRepository reviewRepository) {
    this.providerRepository = providerRepository;
    this.reviewRepository = reviewRepository;
}


    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    public Provider updateProvider(Long id, Provider updatedProvider) {
        return providerRepository.findById(id).map(provider -> {
            provider.setName(updatedProvider.getName());
            provider.setEmail(updatedProvider.getEmail());
            provider.setPhone(updatedProvider.getPhone());
            provider.setAddress(updatedProvider.getAddress());
            provider.setExpertise(updatedProvider.getExpertise());
            provider.setAvailability(updatedProvider.getAvailability());
            provider.setPricingDetails(updatedProvider.getPricingDetails());
            return providerRepository.save(provider);
        }).orElse(null);
    }

    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    public void updateProvider(Provider provider) {
        providerRepository.save(provider);
    }

    @Autowired
    private ServiceLikeRepository likeRepo;

    @Autowired
    private ServiceCommentRepository commentRepo;

    @Autowired
    private ServiceRepository serviceRepo;

    public int getTotalLikesByProvider(Long providerId) {
        return serviceRepo.findByProviderId(providerId).stream()
            .mapToInt(service -> likeRepo.countByService_Id(service.getId()))
            .sum();
    }

    public int getTotalCommentsByProvider(Long providerId) {
        return serviceRepo.findByProviderId(providerId).stream()
            .mapToInt(service -> commentRepo.countByService_Id(service.getId()))
            .sum();
    }

    public Provider getProviderByIdWithSavedUsers(Long id) {
        return providerRepository.findByIdWithSavedUsers(id).orElse(null);
    }

    public List<Provider> searchProvidersByExpertiseOrBusinessName(String keyword) {
        return providerRepository.findByExpertiseContainingIgnoreCaseOrBusinessNameContainingIgnoreCase(keyword, keyword);
    }

    public List<Provider> searchProvidersByKeyword(String keyword) {
        return providerRepository.searchByKeyword(keyword);
    }

    public Optional<Provider> getServicesByProvider(Long providerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServicesByProvider'");
    }

    public double getAverageRatingForProvider(Long providerId) {
        List<ServicePost> services = serviceRepo.findByProviderId(providerId);
        System.out.println("Services found for provider " + providerId + ": " + services.size());
    
        int totalRating = 0;
        int count = 0;
    
        for (ServicePost service : services) {
            List<Review> reviews = reviewRepository.findByServiceId(service.getId());
            System.out.println("Service " + service.getId() + " has " + reviews.size() + " reviews.");
            for (Review r : reviews) {
                totalRating += r.getRating();
                count++;
            }
        }
    
        double result = count > 0 ? (double) totalRating / count : 0.0;
        System.out.println("Final average: " + result);
        return result;
    }
    
    
    public List<Review> getAllReviewsForProvider(Long providerId) {
        List<Review> reviews = reviewRepository.findByServiceProviderId(providerId);
        System.out.println("✅ Total reviews found for provider " + providerId + ": " + reviews.size());
        return reviews;
    }
    
    
    
    

    public List<RecentEngagementDTO> getRecentEngagementForProvider(Long providerId) {

        return new ArrayList<>();
    }
    

    public List<Review> getReviewsByProvider(Long providerId) {
        List<ServicePost> services = serviceRepo.findByProviderId(providerId);
        List<Review> all = new ArrayList<>();
    
        System.out.println("🔍 Fetching services for provider ID: " + providerId);
        for (ServicePost post : services) {
            System.out.println("➡️ Checking ServicePost ID: " + post.getId());
            List<Review> reviewsForService = reviewRepository.findByServiceId(post.getId());
            System.out.println("📝 Found " + reviewsForService.size() + " review(s) for ServicePost ID: " + post.getId());
            all.addAll(reviewsForService);
        }
    
        System.out.println("✅ Total collected reviews: " + all.size());
        return all;
    }
    
    
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookingsByProviderId(Long providerId) {
        return bookingRepository.findByProviderId(providerId);
    }

    
    

    

    


    public Map<Long, Double> getAverageRatingsForAllProviders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAverageRatingsForAllProviders'");
    }

    
    
    

    
}
