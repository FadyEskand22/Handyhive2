package com.handyHive.handyhive.service;

import com.handyHive.handyhive.model.Service;
import com.handyHive.handyhive.model.Provider;
import com.handyHive.handyhive.repository.ServiceRepository;
import com.handyHive.handyhive.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceManagementService {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    
    public Service getServiceById(Integer id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }
    
    public List<Service> getServicesByProvider(Integer providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));
        return serviceRepository.findByProvider(provider);
    }
    
    public List<Service> getServicesByFilter(String filter) {
        return serviceRepository.findByFilter(filter);
    }
    
    public List<Service> getServicesByPriceRange(Integer maxPrice) {
        return serviceRepository.findByPriceLessThanEqual(maxPrice);
    }
    
    public List<Service> getAvailableServices(Date date) {
        return serviceRepository.findByAvailabilityAfter(date);
    }
    
    public Service createService(Service service) {
        // Basic validation
        if (service.getServiceName() == null || service.getServiceName().trim().isEmpty()) {
            throw new RuntimeException("Service name cannot be empty");
        }
        if (service.getDescription() == null || service.getDescription().trim().isEmpty()) {
            throw new RuntimeException("Service description cannot be empty");
        }
        if (service.getPrice() == null || service.getPrice() <= 0) {
            throw new RuntimeException("Service price must be positive");
        }
        if (service.getFilter() == null || service.getFilter().trim().isEmpty()) {
            throw new RuntimeException("Service filter cannot be empty");
        }
        
        // Validate provider exists
        if (service.getProvider() == null || service.getProvider().getProviderId() == null) {
            throw new RuntimeException("Provider is required");
        }
        
        Provider provider = providerRepository.findById(service.getProvider().getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        service.setProvider(provider);
        
        // Set availability to current date if not provided
        if (service.getAvailability() == null) {
            service.setAvailability(new Date());
        }
        
        return serviceRepository.save(service);
    }
    
    public Service updateService(Integer id, Service serviceDetails) {
        Service service = getServiceById(id);
        
        // Update fields
        service.setServiceName(serviceDetails.getServiceName());
        service.setDescription(serviceDetails.getDescription());
        service.setFilter(serviceDetails.getFilter());
        service.setPrice(serviceDetails.getPrice());
        service.setAvailability(serviceDetails.getAvailability());
        
        // Update provider if changed
        if (serviceDetails.getProvider() != null && 
            serviceDetails.getProvider().getProviderId() != null &&
            !serviceDetails.getProvider().getProviderId().equals(service.getProvider().getProviderId())) {
            
            Provider provider = providerRepository.findById(serviceDetails.getProvider().getProviderId())
                    .orElseThrow(() -> new RuntimeException("Provider not found"));
            service.setProvider(provider);
        }
        
        return serviceRepository.save(service);
    }
    
    public void deleteService(Integer id) {
        Service service = getServiceById(id);
        serviceRepository.delete(service);
    }
} 