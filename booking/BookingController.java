package com.handyHive23.handyHive23.booking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking) {
        booking.setStatus("Pending");
        bookingService.createBooking(booking);
        return "redirect:/customers/profile/" + booking.getCustomer().getId();
    }

    @GetMapping("/{id}")
    public String getBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id).orElse(null);
        model.addAttribute("booking", booking);
        return "booking-detail";
    }

    @GetMapping("/all")
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "booking-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings/all";
    }
}
