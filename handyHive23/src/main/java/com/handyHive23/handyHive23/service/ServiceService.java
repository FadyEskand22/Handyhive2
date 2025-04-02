package com.handyHive23.handyHive23.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ProviderService createService(ProviderService service) {
        return serviceRepository.save(service);
    }

    public List<ProviderService> getServicesByProvider(Long providerId) {
        return serviceRepository.findByProviderId(providerId);
    }

    public Optional<ProviderService> updateService(Long serviceId, ProviderService updatedService) {
        return serviceRepository.findById(serviceId).map(service -> {
            service.setTitle(updatedService.getTitle());
            service.setDescription(updatedService.getDescription());
            service.setPrice(updatedService.getPrice());
            service.setAvailability(updatedService.getAvailability());
            return serviceRepository.save(service);
        });
    }
    
    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    public List<ProviderService> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ProviderService> searchServicesByExpertise(String expertise) {
        return serviceRepository.searchByExpertise(expertise);
    }    
    
    
}
