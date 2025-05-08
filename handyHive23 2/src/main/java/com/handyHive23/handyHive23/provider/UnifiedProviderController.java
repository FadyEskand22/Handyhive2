package com.handyHive23.handyHive23.provider;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.handyHive23.handyHive23.review.Review;
import com.handyHive23.handyHive23.review.ReviewDTO;
import com.handyHive23.handyHive23.service.ServiceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/providers")
public class UnifiedProviderController {

    private final ProviderService providerService;
    @Autowired
    private ServiceRepository serviceRepo;


    public UnifiedProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // ------------------------ Web View Routes ------------------------

    @PostMapping("/update/{id}")
    public String updateProviderInfo(@PathVariable Long id,
                                     @ModelAttribute Provider updatedProvider,
                                     Model model) {
        Provider existing = providerService.getProviderById(id).orElse(null);
        if (existing == null) return "redirect:/login";

        existing.setBusinessName(updatedProvider.getBusinessName());
        existing.setName(updatedProvider.getName());
        existing.setEmail(updatedProvider.getEmail());
        existing.setPhone(updatedProvider.getPhone());
        existing.setAddress(updatedProvider.getAddress());
        existing.setExpertise(updatedProvider.getExpertise());
        existing.setAvailability(updatedProvider.getAvailability());
        existing.setPricingDetails(updatedProvider.getPricingDetails());

        providerService.updateProvider(existing);
        model.addAttribute("provider", existing);
        return "provider_profile";
    }

    


    @PostMapping("/upload-profile-picture/{id}")
@ResponseBody
public String uploadProfilePicture(@PathVariable Long id,
                                   @RequestParam("image") MultipartFile image,
                                   HttpSession session) {
    try {
        Provider provider = providerService.getProviderById(id).orElse(null);
        if (provider == null) return "/default-profile.jpg";

        String filename = "provider_" + id + "_" + image.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads/" + filename);
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, image.getBytes());

        String relativePath = "/uploads/" + filename;
        provider.setProfilePicture(relativePath);
        providerService.updateProvider(provider);

        
        session.setAttribute("provider", providerService.getProviderById(id).orElse(provider));

        return relativePath;
    } catch (IOException e) {
        e.printStackTrace();
        return "/default-profile.jpg";
    }
}




    @PostMapping("/update-availability/{id}")
    public String updateAvailability(@PathVariable Long id,
                                     @ModelAttribute Provider updatedProvider,
                                     Model model) {
        Provider existing = providerService.getProviderById(id).orElse(null);
        if (existing == null) return "redirect:/login";

        existing.setSunStart(updatedProvider.getSunStart());
        existing.setSunEnd(updatedProvider.getSunEnd());
        existing.setMonStart(updatedProvider.getMonStart());
        existing.setMonEnd(updatedProvider.getMonEnd());
        existing.setTueStart(updatedProvider.getTueStart());
        existing.setTueEnd(updatedProvider.getTueEnd());
        existing.setWedStart(updatedProvider.getWedStart());
        existing.setWedEnd(updatedProvider.getWedEnd());
        existing.setThuStart(updatedProvider.getThuStart());
        existing.setThuEnd(updatedProvider.getThuEnd());
        existing.setFriStart(updatedProvider.getFriStart());
        existing.setFriEnd(updatedProvider.getFriEnd());
        existing.setSatStart(updatedProvider.getSatStart());
        existing.setSatEnd(updatedProvider.getSatEnd());

        providerService.updateProvider(existing);
        model.addAttribute("provider", existing);
        model.addAttribute("providerId", existing.getId());

        return "provider_profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteProvider(@PathVariable Long id, HttpSession session) {
        providerService.deleteProvider(id);
        session.invalidate(); // log out user
        return "redirect:/login";
    }


    // ------------------------ REST API Routes ------------------------

    @GetMapping("/api")
    @ResponseBody
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Provider> getProvider(@PathVariable Long id) {
        return providerService.getProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api")
    @ResponseBody
    public Provider createProvider(@RequestBody Provider provider) {
        return providerService.createProvider(provider);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider updatedProvider) {
        Provider result = providerService.updateProvider(id, updatedProvider);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
public String showStatistics(Model model, HttpSession session) {
    Provider providerFromSession = (Provider) session.getAttribute("provider");
    if (providerFromSession == null) return "redirect:/login";

    Provider provider = providerService.getProviderByIdWithSavedUsers(providerFromSession.getId());
    if (provider == null) return "redirect:/login";

    int totalPosts = serviceRepo.findByProviderId(provider.getId()).size();
    int totalLikes = providerService.getTotalLikesByProvider(provider.getId());
    int totalComments = providerService.getTotalCommentsByProvider(provider.getId());
    int savedCount = provider.getSavedByUsers() != null ? provider.getSavedByUsers().size() : 0;

    double avgRating = providerService.getAverageRatingForProvider(provider.getId());
    List<Review> reviews = providerService.getReviewsByProvider(provider.getId());


    model.addAttribute("provider", provider);
    model.addAttribute("postCount", totalPosts);
    model.addAttribute("likeCount", totalLikes);
    model.addAttribute("commentCount", totalComments);
    model.addAttribute("savedCount", savedCount); // <- was likely missing
    model.addAttribute("averageRating", avgRating); // <- critical fix
    model.addAttribute("allReviews", reviews);
    List<RecentEngagementDTO> recentEngagement = providerService.getRecentEngagementForProvider(provider.getId());
model.addAttribute("recentEngagement", recentEngagement);  // ✅ Add this line
     // <- required for list rendering

    return "provider_statistics";
}





}


