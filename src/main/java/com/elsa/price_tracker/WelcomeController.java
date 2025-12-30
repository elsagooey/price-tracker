package com.elsa.price_tracker;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WelcomeController {

    private final ProductRepository repository;
    private final ScraperService scraper; 

   
    public WelcomeController(ProductRepository repository, ScraperService scraper) {
        this.repository = repository;
        this.scraper = scraper;
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
        Double newPrice = scraper.getPrice(product.getUrl()); 
        
        if (newPrice != null) {
            product.setCurrentPrice(newPrice);
            repository.save(product);
            return "Success! Scraped price for " + product.getName() + ": " + newPrice;
        }
        return "Scraping failed for URL: " + product.getUrl();
    }

    @PostMapping("/api/products")
public Product addProduct(@RequestBody Product product) {
    if (product.getUrl() == null || product.getUrl().isEmpty()) {
        throw new IllegalArgumentException("URL cannot be empty");
    }

    Double price = scraper.getPrice(product.getUrl());
    product.setCurrentPrice(price);
    product.setLastChecked(java.time.LocalDateTime.now());
    return repository.save(product);
}

    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable Long id) {  
        repository.deleteById(id);
    }
}