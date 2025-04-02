package com.handyHive.handyhive.service;

import com.handyHive.handyhive.model.Booking;
import com.handyHive.handyhive.model.User;
import com.handyHive.handyhive.model.Provider;
import com.handyHive.handyhive.model.Service;
import com.handyHive.handyhive.repository.BookingRepository;
import com.handyHive.handyhive.repository.UserRepository;
import com.handyHive.handyhive.repository.ProviderRepository;
import com.handyHive.handyhive.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }
    
    public List<Booking> getBookingsByCustomer(Integer customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        return bookingRepository.findByCustomer(customer);
    }
    
    public List<Booking> getBookingsByProvider(Integer providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));
        return bookingRepository.findByProvider(provider);
    }
    
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }
    
    public List<Booking> getBookingsByDateRange(Date startDate, Date endDate) {
        return bookingRepository.findByBookingDateBetween(startDate, endDate);
    }
    
    public List<Booking> getCustomerBookingsByStatus(Integer customerId, String status) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
        return bookingRepository.findByCustomerAndStatus(customer, status);
    }
    
    public List<Booking> getProviderBookingsByStatus(Integer providerId, String status) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));
        return bookingRepository.findByProviderAndStatus(provider, status);
    }
    
    public Booking createBooking(Booking booking) {
        // Validate customer
        if (booking.getCustomer() == null || booking.getCustomer().getUserId() == null) {
            throw new RuntimeException("Customer is required");
        }
        User customer = userRepository.findById(booking.getCustomer().getUserId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        booking.setCustomer(customer);
        
        // Validate provider
        if (booking.getProvider() == null || booking.getProvider().getProviderId() == null) {
            throw new RuntimeException("Provider is required");
        }
        Provider provider = providerRepository.findById(booking.getProvider().getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        booking.setProvider(provider);
        
        // Validate service
        if (booking.getService() == null || booking.getService().getServiceId() == null) {
            throw new RuntimeException("Service is required");
        }
        Service service = serviceRepository.findById(booking.getService().getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        booking.setService(service);
        
        // Validate booking date
        if (booking.getBookingDate() == null) {
            throw new RuntimeException("Booking date is required");
        }
        if (booking.getBookingDate().before(new Date())) {
            throw new RuntimeException("Booking date cannot be in the past");
        }
        
        // Set initial status
        booking.setStatus("PENDING");
        
        return bookingRepository.save(booking);
    }
    
    public Booking updateBookingStatus(Integer id, String status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
    
    public void deleteBooking(Integer id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
} 