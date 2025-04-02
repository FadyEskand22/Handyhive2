package com.handyHive.handyhive.controller;

import com.handyHive.handyhive.model.Provider;
import com.handyHive.handyhive.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin(origins = "*")
public class ProviderController {
    
    @Autowired
    private ProviderService providerService;

    @GetMapping
    public ResponseEntity<?> getAllProviders() {
        try {
            List<Provider> providers = providerService.getAllProviders();
            return ResponseEntity.ok(providers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProviderById(@PathVariable Integer id) {
        try {
            Provider provider = providerService.getProviderById(id);
            return ResponseEntity.ok(provider);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/business/{businessName}")
    public ResponseEntity<Provider> getProviderByBusinessName(@PathVariable String businessName) {
        return ResponseEntity.ok(providerService.getProviderByBusinessName(businessName));
    }

    @GetMapping("/available")
    public List<Provider> getAvailableProviders(@RequestParam Date date) {
        return providerService.getAvailableProviders(date);
    }

    @PostMapping
    public ResponseEntity<?> createProvider(@RequestBody Provider provider) {
        try {
            Provider createdProvider = providerService.createProvider(provider);
            return ResponseEntity.ok(createdProvider);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvider(@PathVariable Integer id, @RequestBody Provider providerDetails) {
        try {
            Provider updatedProvider = providerService.updateProvider(id, providerDetails);
            return ResponseEntity.ok(updatedProvider);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvider(@PathVariable Integer id) {
        try {
            providerService.deleteProvider(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
} 