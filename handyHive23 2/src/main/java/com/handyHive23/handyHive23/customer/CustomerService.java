package com.handyHive23.handyHive23.customer;

import org.springframework.stereotype.Service;

import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderRepository;
import org.hibernate.Hibernate;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProviderRepository providerRepository;

    public CustomerService(CustomerRepository customerRepository, ProviderRepository providerRepository) {
        this.customerRepository = customerRepository;
        this.providerRepository = providerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        });
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
public boolean toggleSaveProvider(Long customerId, Long providerId) {
    Customer customer = customerRepository.findByIdWithSavedProviders(customerId)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
    Provider provider = providerRepository.findById(providerId)
        .orElseThrow(() -> new RuntimeException("Provider not found"));

    Set<Provider> saved = customer.getSavedProviders();

    boolean nowSaved;
    if (saved.contains(provider)) {
        saved.remove(provider);
        nowSaved = false;
    } else {
        saved.add(provider);
        nowSaved = true;
    }

    // No need to replace the whole set anymore
    customerRepository.save(customer);
    return nowSaved;
}


  public Set<Provider> getSavedProviders(Long customerId) {
    return customerRepository.findById(customerId)
             .map(Customer::getSavedProviders)
             .orElse(Collections.emptySet());
  }


    
}
