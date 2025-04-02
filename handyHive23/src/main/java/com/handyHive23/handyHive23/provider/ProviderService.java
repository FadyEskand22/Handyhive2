package com.handyHive23.handyHive23.provider;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
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
}
