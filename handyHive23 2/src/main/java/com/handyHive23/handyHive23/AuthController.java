package com.handyHive23.handyHive23;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.customer.CustomerService;
import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderService;
import com.handyHive23.handyHive23.service.CommentEntry;
import com.handyHive23.handyHive23.service.ServicePost;
import com.handyHive23.handyHive23.service.ServiceService;

import java.util.*;


import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AuthController {

    private final CustomerService customerService;
    private final ProviderService providerService;
    private final ServiceService serviceService;


    public AuthController(CustomerService customerService,
                      ProviderService providerService,
                      ServiceService serviceService) {
    this.customerService = customerService;
    this.providerService = providerService;
    this.serviceService = serviceService;
}



    

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
public String loginUser(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

    // Check if it's a customer
    var customerOpt = customerService.getAllCustomers()
            .stream()
            .filter(c -> email.equalsIgnoreCase(c.getEmail()) && password.equals(c.getPassword()))
            .findFirst();

    if (customerOpt.isPresent()) {
        session.setAttribute("customer", customerOpt.get());
        model.addAttribute("customer", customerOpt.get());
        return "customer-profile";
    }

    // Check if it's a provider
    var providerOpt = providerService.getAllProviders()
            .stream()
            .filter(p -> email.equalsIgnoreCase(p.getEmail()) && password.equals(p.getPassword()))
            .findFirst();

    if (providerOpt.isPresent()) {
        session.setAttribute("provider", providerOpt.get()); // ✅ this line is essential
        model.addAttribute("provider", providerOpt.get());
        return "provider_profile";
    }

    model.addAttribute("error", "Invalid email or password");
    return "login";
}


    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @GetMapping("/register/customer")
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register_customer";
    }

    @PostMapping("/register/customer")
    public String submitCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/register/provider")
    public String providerForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "register_provider";
    }

    @PostMapping("/register/provider")
    public String submitProvider(@ModelAttribute Provider provider) {
        providerService.createProvider(provider);
        return "redirect:/login";
    }

    @PostMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
}

@GetMapping("/index")
public String showIndex(Model model) {
    List<ServicePost> posts = serviceService.getAllServices();

    for (ServicePost post : posts) {
        post.setRecentComments(
            serviceService.getCommentsForPost(post.getId()).stream().map(c -> {
                CommentEntry entry = new CommentEntry();
                entry.setUsername(c.getCustomer().getName()); // or .getEmail()
                entry.setText(c.getContent());
                entry.setDate(c.getCommentedAt().toString());
                return entry;
            }).toList()
        );
        post.setLikedByUsers(new HashSet<>()); // Optional if you want likes preloaded
    }

    model.addAttribute("posts", posts);
    return "index";
}



@GetMapping("/provider/profile")
public String showProviderProfile(HttpSession session, Model model) {
    Provider provider = (Provider) session.getAttribute("provider");
    if (provider == null) return "redirect:/login";

    model.addAttribute("provider", provider);
    return "provider_profile"; // Matches provider_profile.ftlh
}

@PostMapping("/upload-profile-picture/{id}")
@ResponseBody
public String uploadProfilePicture(@PathVariable Long id,
                                   @RequestParam("image") MultipartFile image) {
    try {
        Provider provider = providerService.getProviderById(id).orElse(null);
        if (provider == null) return "/default-profile.jpg";

       
        String filename = "provider_" + id + "_" + image.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/" + filename);  
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, image.getBytes());

        
        String relativePath = "/uploads/" + filename;
        provider.setProfilePicture(relativePath);
        providerService.updateProvider(provider);

    
        return relativePath;
    } catch (IOException e) {
        e.printStackTrace();
        return "/default-profile.jpg";
    }
}
@GetMapping("/posts")
public String showPostForm(HttpSession session, Model model) {
    Provider provider = (Provider) session.getAttribute("provider");
    if (provider == null) return "redirect:/login";

    model.addAttribute("provider", provider);

    List<ServicePost> posts = serviceService.getServicesByProvider(provider.getId());
    model.addAttribute("posts", posts);

    return "posts";
}





}
