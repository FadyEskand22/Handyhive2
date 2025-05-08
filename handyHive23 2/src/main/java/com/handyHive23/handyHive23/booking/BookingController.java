package com.handyHive23.handyHive23.booking;
 
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;

import com.handyHive23.handyHive23.customer.Customer;

import jakarta.servlet.http.HttpSession;

import java.util.List;
 
 @Controller
 @RequestMapping("/bookings")
 public class BookingController {
 
     private final BookingService bookingService;
 
     public BookingController(BookingService bookingService) {
         this.bookingService = bookingService;
     }
 
     @PostMapping("/create")
public String createBooking(@ModelAttribute Booking booking, HttpSession session) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return "redirect:/login";

    booking.setCustomer(customer); // Set the customer explicitly
    booking.setStatus("Confirmed"); // or "Pending" if you prefer
    bookingService.createBooking(booking);

    return "redirect:/bookings/all";
}

 
     @GetMapping("/view/{id}")
     public String getBooking(@PathVariable Long id, Model model) {
         Booking booking = bookingService.getBookingById(id).orElse(null);
         model.addAttribute("booking", booking);
         return "booking-detail";
     }
 
     @GetMapping("/all")
public String getCustomerBookings(HttpSession session, Model model) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return "redirect:/login";

    // Only get bookings for the logged-in customer
    List<Booking> allBookings = bookingService.getAllBookings();

    // Filter safely
    List<Booking> customerBookings = allBookings.stream()
        .filter(b -> b.getCustomer() != null && b.getCustomer().getId().equals(customer.getId()))
        .toList();

    model.addAttribute("bookings", customerBookings);
    return "bookings"; // Make sure this matches your .ftlh template
}


 
     @PostMapping("/delete/{id}")
     public String deleteBooking(@PathVariable Long id) {
         bookingService.deleteBooking(id);
         return "redirect:/bookings/all";
     }
 }