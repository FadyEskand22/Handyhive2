package com.handyHive.handyhive.repository;

import com.handyHive.handyhive.model.Booking;
import com.handyHive.handyhive.model.User;
import com.handyHive.handyhive.model.Provider;
import com.handyHive.handyhive.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomer(User customer);
    List<Booking> findByProvider(Provider provider);
    List<Booking> findByService(Service service);
    List<Booking> findByStatus(String status);
    List<Booking> findByBookingDateBetween(Date startDate, Date endDate);
    List<Booking> findByCustomerAndStatus(User customer, String status);
    List<Booking> findByProviderAndStatus(Provider provider, String status);
} 