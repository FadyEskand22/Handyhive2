package com.handyHive23.handyHive23.service;

import com.handyHive23.handyHive23.provider.ProviderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ProviderRepository providerRepository;

    public ServiceController(ServiceService serviceService, ProviderRepository providerRepository) {
        this.serviceService = serviceService;
        this.providerRepository = providerRepository;
    }

    @PostMapping("/{providerId}")
    public ResponseEntity<ProviderService> createService(@PathVariable Long providerId, @RequestBody ProviderService service) {
        return providerRepository.findById(providerId).map(provider -> {
            service.setProvider(provider);
            return ResponseEntity.ok(serviceService.createService(service));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ProviderService>> getServicesByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(serviceService.getServicesByProvider(providerId));
    }
    
    @PutMapping("/{serviceId}")
    public ResponseEntity<ProviderService> updateService(@PathVariable Long serviceId, @RequestBody ProviderService updatedService) {
        return serviceService.updateService(serviceId, updatedService)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProviderService>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProviderService>> searchServicesByExpertise(@RequestParam String expertise) {
        return ResponseEntity.ok(serviceService.searchServicesByExpertise(expertise));
    }




}
