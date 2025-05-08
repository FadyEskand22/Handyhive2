package com.handyHive23.handyHive23.customer;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.handyHive23.handyHive23.provider.Provider;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ===================== JSON REST API =====================
    @PostMapping
    @ResponseBody
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== Web (Freemarker) Pages =====================

    @GetMapping("/profile/{id}")
public String showCustomerProfile(@PathVariable Long id, Model model) {
    Customer customer = customerService.getCustomerById(id).orElse(null);
    if (customer == null) return "redirect:/login";

    // ✅ JOIN FETCH version so savedProviders is loaded
    Set<Provider> saved = customerService.getSavedProviders(id);

    model.addAttribute("customer", customer);
    model.addAttribute("savedProviders", saved); // ✅ this is what was missing

    return "customer-profile";
}


    @PostMapping("/update/{id}")
    public String updateCustomerInfo(@PathVariable Long id, @ModelAttribute Customer updatedCustomer, Model model) {
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

    @PostMapping("/upload-profile-picture/{id}")
    @ResponseBody
    public String uploadProfilePicture(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
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

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomerAccount(@PathVariable Long id, HttpSession session) {
        customerService.deleteCustomer(id);
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/save-provider/{providerId}")
@ResponseBody
public ResponseEntity<String> toggleSaveProvider(@PathVariable Long providerId, HttpSession session) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return ResponseEntity.status(401).body("Not logged in");

    boolean saved = customerService.toggleSaveProvider(customer.getId(), providerId);
    return ResponseEntity.ok(saved ? "saved" : "unsaved");
}

@GetMapping("/saved")
public String viewSavedProviders(HttpSession session, Model model) {
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) return "redirect:/login";

    Set<Provider> saved = customerService.getSavedProviders(customer.getId());
    model.addAttribute("savedProviders", saved);
    model.addAttribute("customer", customer);
    return "saved-providers"; // You'll build this page later
}


    
}
