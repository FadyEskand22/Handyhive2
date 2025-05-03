package com.handyHive23.handyHive23.service;

import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ProviderRepository providerRepository;

    public ServiceController(ServiceService serviceService, ProviderRepository providerRepository) {
        this.serviceService = serviceService;
        this.providerRepository = providerRepository;
    }

    @PostMapping("/{providerId}")
    public String createService(@PathVariable Long providerId,
                                @RequestParam String title,
                                @RequestParam String description,
                                @RequestParam Double price,
                                @RequestParam String availability,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                HttpSession session) {
        Provider provider = (Provider) session.getAttribute("provider");
        if (provider == null) return "redirect:/login";

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            try {
                String filename = "post_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path staticUploadPath = Paths.get("src/main/resources/static/uploads/" + filename);
                Path runtimeUploadPath = Paths.get("target/classes/static/uploads/" + filename);

                Files.createDirectories(staticUploadPath.getParent());
                Files.createDirectories(runtimeUploadPath.getParent());

                Files.write(staticUploadPath, image.getBytes());
                Files.write(runtimeUploadPath, image.getBytes());

                imagePath = "/uploads/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ServicePost post = new ServicePost();
        post.setTitle(title);
        post.setDescription(description);
        post.setPrice(price);
        post.setAvailability(availability);
        post.setProvider(provider);
        post.setImagePath(imagePath);

        serviceService.createService(post);
        return "redirect:/posts";
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServicePost>> getServicesByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(serviceService.getServicesByProvider(providerId));
    }

    // Moved this method outside the "/services" scope for cleaner URL
    @GetMapping("/find-services")
    public String showFindServicesPage(@RequestParam(value = "search", required = false) String search, Model model) {
        List<ServicePost> services;

        if (search != null && !search.isBlank()) {
            services = serviceService.searchServicesByExpertise(search);
            model.addAttribute("search", search);
        } else {
            services = serviceService.getAllServices();
        }

        model.addAttribute("services", services);
        return "find-services";
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServicePost> updateService(@PathVariable Long serviceId, @RequestBody ServicePost updatedService) {
        return serviceService.updateService(serviceId, updatedService)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServicePost>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServicePost>> searchServicesByExpertise(@RequestParam String expertise) {
        return ResponseEntity.ok(serviceService.searchServicesByExpertise(expertise));
    }

    @PostMapping("/delete/{serviceId}")
    public String deleteServiceViaPost(@PathVariable Long serviceId, HttpSession session, Model model) {
        serviceService.deleteService(serviceId);

        Provider provider = (Provider) session.getAttribute("provider");
        if (provider != null) {
            model.addAttribute("provider", provider);
            model.addAttribute("posts", serviceService.getServicesByProvider(provider.getId()));
        }

        return "redirect:/posts";
    }
}
