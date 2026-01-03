package com.elsa.price_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component 
public class TestDataLoader implements CommandLineRunner {

    private final ProductRepository repository;

    
    public TestDataLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Seeding Initial Test Data ---");

      
        Product testItem = new Product();
        testItem.setName("need to remove");
        testItem.setUrl("https://books.toscrape.com/catalogue/a-light-in-the-attic_1000/index.html");
        testItem.setTargetPrice(100.0);
        testItem.setCurrentPrice(150.0);

   
        repository.save(testItem);

        System.out.println("Successfully added: " + testItem.getName());
    }
}