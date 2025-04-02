package com.handyHive.handyhive.controller;

import com.handyHive.handyhive.model.Service;
import com.handyHive.handyhive.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@Validated
public class ServiceController {
    
    @Autowired
    private ServiceManagementService serviceManagementService;

    @GetMapping
    public List<Service> getAllServices() {
        return serviceManagementService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceManagementService.getServiceById(id));
    }

    @GetMapping("/provider/{providerId}")
    public List<Service> getServicesByProvider(@PathVariable Integer providerId) {
        return serviceManagementService.getServicesByProvider(providerId);
    }

    @GetMapping("/filter/{filter}")
    public List<Service> getServicesByFilter(@PathVariable String filter) {
        return serviceManagementService.getServicesByFilter(filter);
    }

    @GetMapping("/price/{maxPrice}")
    public List<Service> getServicesByPriceRange(@PathVariable Integer maxPrice) {
        return serviceManagementService.getServicesByPriceRange(maxPrice);
    }

    @GetMapping("/available")
    public List<Service> getAvailableServices(@RequestParam Date date) {
        return serviceManagementService.getAvailableServices(date);
    }

    @PostMapping
    public ResponseEntity<Service> createService(@Valid @RequestBody Service service) {
        return ResponseEntity.ok(serviceManagementService.createService(service));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(
            @PathVariable Integer id,
            @Valid @RequestBody Service serviceDetails) {
        return ResponseEntity.ok(serviceManagementService.updateService(id, serviceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        serviceManagementService.deleteService(id);
        return ResponseEntity.ok().build();
    }
} 