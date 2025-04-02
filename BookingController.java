package com.handyHive.handyhive.controller;

import com.handyHive.handyhive.model.Booking;
import com.handyHive.handyhive.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomer(@PathVariable Integer customerId) {
        return bookingService.getBookingsByCustomer(customerId);
    }

    @GetMapping("/provider/{providerId}")
    public List<Booking> getBookingsByProvider(@PathVariable Integer providerId) {
        return bookingService.getBookingsByProvider(providerId);
    }

    @GetMapping("/status/{status}")
    public List<Booking> getBookingsByStatus(@PathVariable String status) {
        return bookingService.getBookingsByStatus(status);
    }

    @GetMapping("/date-range")
    public List<Booking> getBookingsByDateRange(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return bookingService.getBookingsByDateRange(startDate, endDate);
    }

    @GetMapping("/customer/{customerId}/status/{status}")
    public List<Booking> getCustomerBookingsByStatus(
            @PathVariable Integer customerId,
            @PathVariable String status) {
        return bookingService.getCustomerBookingsByStatus(customerId, status);
    }

    @GetMapping("/provider/{providerId}/status/{status}")
    public List<Booking> getProviderBookingsByStatus(
            @PathVariable Integer providerId,
            @PathVariable String status) {
        return bookingService.getProviderBookingsByStatus(providerId, status);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
} 