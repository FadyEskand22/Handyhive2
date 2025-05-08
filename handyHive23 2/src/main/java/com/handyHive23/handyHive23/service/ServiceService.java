package com.handyHive23.handyHive23.service;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.customer.CustomerRepository;
import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private final ProviderRepository providerRepository;

    public ServiceService(ServiceRepository serviceRepository,
                          CustomerRepository customerRepository,
                          ProviderRepository providerRepository) {
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.providerRepository = providerRepository;
    }

    public List<ServicePost> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ServicePost> getServicesByProvider(Long providerId) {
        return serviceRepository.findByProviderId(providerId);
    }

    public void createService(ServicePost post) {
        serviceRepository.save(post);
    }

    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public Optional<ServicePost> updateService(Long serviceId, ServicePost updated) {
        return serviceRepository.findById(serviceId).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setPrice(updated.getPrice());
            existing.setAvailability(updated.getAvailability());
            return serviceRepository.save(existing);
        });
    }

    public List<ServicePost> searchServicesByExpertise(String expertise) {
        return serviceRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(expertise, expertise);
    }
    

    public Optional<ServicePost> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }


    @Autowired
    private ServiceLikeRepository likeRepo;

    @Autowired
    private ServiceCommentRepository commentRepo;


    @Transactional
public void toggleLike(Long postId, HttpSession session) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return;

    ServicePost post = serviceRepository.findById(postId).orElse(null);
    if (post == null) return;

    // Check if this customer already liked the post
    List<ServiceLike> existingLikes = likeRepo.findByService_Id(postId);
    Optional<ServiceLike> existing = existingLikes.stream()
        .filter(like -> like.getCustomer().getId().equals(customer.getId()))
        .findFirst();

    if (existing.isPresent()) {
        likeRepo.delete(existing.get());
    } else {
        ServiceLike like = new ServiceLike();
        like.setService(post);
        like.setCustomer(customer);
        like.setLikedAt(LocalDateTime.now());
        likeRepo.save(like);
    }
}


    @Transactional
public void addComment(Long postId, String text, HttpSession session) {
    ServicePost post = serviceRepository.findById(postId).orElse(null);
    if (post == null) return;

    ServiceComment comment = new ServiceComment();
    comment.setService(post);
    comment.setContent(text);
    comment.setCommentedAt(LocalDateTime.now());

    // Check session for customer or provider
    Customer customer = (Customer) session.getAttribute("customer");
    Provider provider = (Provider) session.getAttribute("provider");

    if (customer != null) {
        comment.setCustomer(customer);
    } else if (provider != null) {
        
        return; 
    } else {
        return;
    }

    commentRepo.save(comment);
}

    public int getLikeCount(Long postId) {
        return likeRepo.countByService_Id(postId);
    }

    public List<ServiceComment> getCommentsForPost(Long postId) {
        return commentRepo.findByService_Id(postId);
    }

    public boolean hasUserLiked(Long postId, Long userId) {
        Optional<Customer> customer = customerRepository.findById(userId);
        Optional<Provider> provider = providerRepository.findById(userId);
    
        String username = customer.map(Customer::getName)
                .orElse(provider.map(Provider::getName).orElse(null));
    
        if (username == null) return false;
    
        return serviceRepository.findById(postId)
                .map(post -> post.getLikedByUsers().contains(username))
                .orElse(false);
    }
    

}
