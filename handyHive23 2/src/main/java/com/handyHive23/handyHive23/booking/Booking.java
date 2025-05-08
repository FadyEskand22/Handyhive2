package com.handyHive23.handyHive23.booking;
 
 import com.handyHive23.handyHive23.customer.Customer;
 import com.handyHive23.handyHive23.provider.Provider;
 import jakarta.persistence.*;
 
 import java.time.LocalDate;
 import java.time.LocalTime;
 
 @Entity
 @Table(name = "booking")
 public class Booking {
 
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long bookingId;
 
     @ManyToOne
     @JoinColumn(name = "customer_id")
     private Customer customer;
 
     @ManyToOne
     @JoinColumn(name = "provider_id")
     private Provider provider;
 
     private LocalDate appointmentDate;
     private LocalTime appointmentTime;
     private String status;
 
     // Manually added getters and setters
 
     public Long getBookingId() {
         return bookingId;
     }
 
     public void setBookingId(Long bookingId) {
         this.bookingId = bookingId;
     }
 
     public Customer getCustomer() {
         return customer;
     }
 
     public void setCustomer(Customer customer) {
         this.customer = customer;
     }
 
     public Provider getProvider() {
         return provider;
     }
 
     public void setProvider(Provider provider) {
         this.provider = provider;
     }
 
     public LocalDate getAppointmentDate() {
         return appointmentDate;
     }
 
     public void setAppointmentDate(LocalDate appointmentDate) {
         this.appointmentDate = appointmentDate;
     }
 
     public LocalTime getAppointmentTime() {
         return appointmentTime;
     }
 
     public void setAppointmentTime(LocalTime appointmentTime) {
         this.appointmentTime = appointmentTime;
     }
 
     public String getStatus() {
         return status;
     }
 
     public void setStatus(String status) {
         this.status = status;
     }
 }