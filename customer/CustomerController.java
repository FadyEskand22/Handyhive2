package com.handyHive23.handyHive23.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ------------------------ Web View Routes ------------------------

    @GetMapping("/profile/{id}")
    public String showCustomerProfile(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id).orElse(null);
        if (customer == null) return "redirect:/login";
        model.addAttribute("customer", customer);
        return "customer-profile"; // ensure this matches the FTL file name
    }

    // Handle updating customer profile information
    @PostMapping("/update/{id}")
    public String updateCustomerInfo(@PathVariable Long id,
                                     @ModelAttribute Customer updatedCustomer,
                                     Model model) {
        Customer existing = customerService.getCustomerById(id).orElse(null);
        if (existing == null) return "redirect:/login";

        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddress(updatedCustomer.getAddress());

        customerService.updateCustomer(id, existing);
        model.addAttribute("customer", existing);

        return "customer-profile";
    }

    // Handle the profile picture upload
    @PostMapping("/upload-profile-picture/{id}")
    @ResponseBody
    public String uploadProfilePicture(@PathVariable Long id,
                                       @RequestParam("image") MultipartFile image) {
        try {
            Customer customer = customerService.getCustomerById(id).orElse(null);
            if (customer == null) return "/default-profile.jpg";

            String filename = "customer_" + id + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads/" + filename);
            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, image.getBytes());

            String relativePath = "/uploads/" + filename;
            customer.setProfilePicture(relativePath);
            customerService.updateCustomer(id, customer);

            return relativePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "/default-profile.jpg";
        }
    }

    // ------------------------ Logout ------------------------

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ------------------------ Delete Account ------------------------

    @PostMapping("/delete/{id}")
    public String deleteCustomerAccount(@PathVariable Long id, HttpSession session) {
        customerService.deleteCustomer(id);
        session.invalidate(); // Log the user out after deletion
        return "redirect:/login";
    }
}
