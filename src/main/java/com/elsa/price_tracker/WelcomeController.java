package com.elsa.price_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<h1>Success!</h1><p>Elsa's Price Tracker is running.</p>";
    }
}