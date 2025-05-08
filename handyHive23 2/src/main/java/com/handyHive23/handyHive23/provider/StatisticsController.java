package com.handyHive23.handyHive23.provider;

import com.handyHive23.handyHive23.review.Review;
import com.handyHive23.handyHive23.review.ReviewRepository;
import com.handyHive23.handyHive23.service.ServiceComment;
import com.handyHive23.handyHive23.service.ServicePost;
import com.handyHive23.handyHive23.service.ServiceService;
import jakarta.servlet.http.HttpSession;

import com.handyHive23.handyHive23.booking.Booking;
import com.handyHive23.handyHive23.provider.PdfGenerator;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class StatisticsController {

    private final ServiceService serviceService;
    private final ProviderService providerService;
    private final ReviewRepository reviewRepository;

    public StatisticsController(ServiceService serviceService, ProviderService providerService, ReviewRepository reviewRepository) {
        this.serviceService = serviceService;
        this.providerService = providerService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/provider/statistics")
    public String viewProviderStatistics(HttpSession session, Model model) {
        Provider sessionProvider = (Provider) session.getAttribute("provider");
if (sessionProvider == null) return "redirect:/login";

Provider provider = providerService.getProviderByIdWithSavedUsers(sessionProvider.getId());


        Long providerId = provider.getId();

        List<ServicePost> posts = serviceService.getServicesByProvider(providerId);
        int postCount = posts.size();
        int likeCount = posts.stream().mapToInt(ServicePost::getLikes).sum();
        int commentCount = providerService.getTotalCommentsByProvider(providerId);
        int savedCount = provider.getSavedByUsers() != null ? provider.getSavedByUsers().size() : 0;

        List<Map<String, String>> recentEngagement = new ArrayList<>();

for (ServicePost post : posts) {
    List<ServiceComment> comments = serviceService.getCommentsForPost(post.getId());
    recentEngagement.sort((a, b) -> b.get("date").compareTo(a.get("date")));

    for (ServiceComment c : comments) {
        if (c.getCustomer() != null) { // Ensure only customer comments
            Map<String, String> entry = new HashMap<>();
            entry.put("username", c.getCustomer().getName());
            entry.put("text", c.getContent());
            entry.put("date", c.getCommentedAt().toString());
            recentEngagement.add(entry);
        }
    }
}


        double avgRating = providerService.getAverageRatingForProvider(providerId);
        List<Review> reviews = providerService.getAllReviewsForProvider(providerId);

        List<Booking> bookings = providerService.getBookingsByProviderId(providerId);
        model.addAttribute("bookings", bookings);


        model.addAttribute("averageRating", avgRating > 0 ? String.format("%.2f", avgRating) : "N/A");
        model.addAttribute("allReviews", reviews);


        model.addAttribute("savedCount", savedCount);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("postCount", postCount);
        model.addAttribute("recentEngagement", recentEngagement);

        return "provider_statistics";
    }

    @GetMapping("/provider/statistics/pdf")
public ResponseEntity<byte[]> generatePdfReport(HttpSession session) throws IOException {
    Provider sessionProvider = (Provider) session.getAttribute("provider");
    if (sessionProvider == null) return ResponseEntity.status(401).build();

    Provider provider = providerService.getProviderByIdWithSavedUsers(sessionProvider.getId());
    Long providerId = provider.getId();

    List<ServicePost> posts = serviceService.getServicesByProvider(providerId);
    int postCount = posts.size();
    int commentCount = providerService.getTotalCommentsByProvider(providerId);
    int savedCount = provider.getSavedByUsers() != null ? provider.getSavedByUsers().size() : 0;
    double avgRating = providerService.getAverageRatingForProvider(providerId);

    List<ServiceComment> allComments = new ArrayList<>();
    for (ServicePost post : posts) {
        allComments.addAll(serviceService.getCommentsForPost(post.getId()));
    }

    List<Review> reviews = providerService.getAllReviewsForProvider(providerId);
    List<Booking> bookings = providerService.getBookingsByProviderId(providerId);

    ByteArrayOutputStream out = PdfGenerator.createStatisticsPdf(
        provider.getBusinessName(),
        postCount,
        commentCount,
        savedCount,
        avgRating,
        allComments,
        reviews,
        bookings
    );

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=provider_statistics.pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(out.toByteArray());
}


    
}
