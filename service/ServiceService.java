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

    public ServicePost createService(ServicePost service) {
        return serviceRepository.save(service);
    }

    public List<ServicePost> getServicesByProvider(Long providerId) {
        return serviceRepository.findByProviderId(providerId);
    }

    public Optional<ServicePost> updateService(Long serviceId, ServicePost updatedService) {
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

    public List<ServicePost> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ServicePost> searchServicesByExpertise(String expertise) {
        return serviceRepository.searchByExpertise(expertise);
    }    
    
    
}
