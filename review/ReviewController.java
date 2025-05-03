package com.handyHive23.handyHive23.review;

import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ProviderService providerService;

    @Autowired
    public ReviewController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // Show the review page for a specific provider
    @GetMapping("/service/{id}")
    public String showReviewForm(@PathVariable Long id, Model model) {
        Provider provider = providerService.getProviderById(id).orElse(null);

        if (provider == null) {
            return "redirect:/error"; // or return a custom error page
        }

        model.addAttribute("provider", provider);
        return "review"; // this resolves to review.ftlh
    }

    // Handle form submission for reviews (stubbed - you can expand this)
    @PostMapping("/submit-review")
    public String submitReview(@RequestParam("providerId") Long providerId,
                               @RequestParam("rating") int rating,
                               @RequestParam("comment") String comment) {
        // Here you would handle saving the review to your database
        System.out.println("Review submitted: Provider ID = " + providerId + ", Rating = " + rating + ", Comment = " + comment);

        // Redirect to confirmation or back to provider page
        return "redirect:/reviews/service/" + providerId;
    }
}
