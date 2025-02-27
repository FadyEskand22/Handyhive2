package com.handyHive.handyhive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Returns login.html
    }

    @GetMapping("/register/provider")
    public String showProviderRegisterPage() {
        return "provider_register"; // Returns provider_register.html
    }

    @GetMapping("/register/shopper")
    public String showShopperRegisterPage() {
        return "shopper_register"; // Returns shopper_register.html
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "homepage"; // Returns homepage.html after login
    }
}