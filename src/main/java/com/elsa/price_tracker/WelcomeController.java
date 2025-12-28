package com.elsa.price_tracker;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private final ProductRepository repository;
    private final ScraperService scraper; // 1. Declare the variable

    // 2. Add 'ScraperService scraper' to the constructor parameters below
    public WelcomeController(ProductRepository repository, ScraperService scraper) {
        this.repository = repository;
        this.scraper = scraper;
    }

    @GetMapping("/")
    public String home() {
        return "<h1>Success!</h1><p>Go to <a href='/api/products'>/api/products</a></p>";
    }

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @GetMapping("/api/test-scrape")
    public String testScrape() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) return "No products in database!";
        
        Product product = products.get(0);
        Double newPrice = scraper.getPrice(product.getUrl()); // Now 'scraper' will be recognized!
        
        if (newPrice != null) {
            product.setCurrentPrice(newPrice);
            repository.save(product);
            return "Success! Scraped price for " + product.getName() + ": " + newPrice;
        }
        return "Scraping failed for URL: " + product.getUrl();
    }
}