package com.handyHive.handyhive.service;

import com.handyHive.handyhive.model.Provider;
import com.handyHive.handyhive.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ProviderService {
    
    @Autowired
    private ProviderRepository providerRepository;
    
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
    
    public Provider getProviderById(Integer id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + id));
    }
    
    public Provider getProviderByBusinessName(String businessName) {
        return providerRepository.findByBusinessName(businessName);
    }
    
    public List<Provider> getAvailableProviders(Date date) {
        return providerRepository.findByAvailabilityAfter(date);
    }
    
    public Provider createProvider(Provider provider) {
        // Basic validation
        if (provider.getBusinessName() == null || provider.getBusinessName().trim().isEmpty()) {
            throw new RuntimeException("Business name cannot be empty");
        }
        if (provider.getServiceDescription() == null || provider.getServiceDescription().trim().isEmpty()) {
            throw new RuntimeException("Service description cannot be empty");
        }
        if (provider.getPrice() == null || provider.getPrice().trim().isEmpty()) {
            throw new RuntimeException("Price cannot be empty");
        }
        
        // Check if business name already exists
        if (providerRepository.existsByBusinessName(provider.getBusinessName())) {
            throw new RuntimeException("Business name already exists");
        }
        
        return providerRepository.save(provider);
    }
    
    public Provider updateProvider(Integer id, Provider providerDetails) {
        Provider provider = getProviderById(id);
        
        // Update fields
        provider.setBusinessName(providerDetails.getBusinessName());
        provider.setServiceDescription(providerDetails.getServiceDescription());
        provider.setPrice(providerDetails.getPrice());
        provider.setAvailability(providerDetails.getAvailability());
        
        return providerRepository.save(provider);
    }
    
    public void deleteProvider(Integer id) {
        Provider provider = getProviderById(id);
        providerRepository.delete(provider);
    }
} 