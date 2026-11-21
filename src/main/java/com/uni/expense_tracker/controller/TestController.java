package com.uni.expense_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/secure")
    public String secureEndpoint(Authentication authentication) {
        // If we get here, the JWT was valid!
        return "Hello, " + authentication.getName() + "! You accessed a protected route.";
    }
}