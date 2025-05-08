package com.handyHive23.handyHive23.review;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.review.ReviewService;
import com.handyHive23.handyHive23.service.ServicePost;
import com.handyHive23.handyHive23.service.ServiceService;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ServiceService serviceService;

    public ReviewController(ReviewService reviewService, ServiceService serviceService) {
        this.reviewService = reviewService;
        this.serviceService = serviceService;
    }

    @GetMapping("/service/{serviceId}")
    public String showReviewForm(@PathVariable Long serviceId, Model model) {
        Optional<ServicePost> serviceOpt = serviceService.getServiceById(serviceId);
        if (serviceOpt.isEmpty()) return "redirect:/services/find-services";

        ServicePost service = serviceOpt.get(); // now you can call methods on it
        model.addAttribute("service", service);
        model.addAttribute("provider", service.getProvider());

        return "review";
    }

    @PostMapping("/submit-review")
public String submitReview(@RequestParam("serviceId") Long serviceId,
                           @RequestParam("rating") int rating,
                           @RequestParam("comment") String comment,
                           HttpSession session) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return "redirect:/customers/login";

    Review review = new Review();
    review.setRating(rating);
    review.setComment(comment);

    reviewService.createReview(customer.getId(), serviceId, review);

    return "redirect:/services/find-services";
}


}
