package com.elsa.price_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // This tells Spring to find and run this class automatically
public class TestDataLoader implements CommandLineRunner {

    private final ProductRepository repository;

    // We "inject" the repository so we can save data
    public TestDataLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Seeding Initial Test Data ---");

        // 1. Create a test product
        Product testItem = new Product();
        testItem.setName("Cool Sneakers");
        testItem.setUrl("https://www.amazon.com/adidas-Womens-Court-Sneaker-White/dp/B0C2JXSHSK/ref=sr_1_1?crid=3IGB7THB7MRMX&dib=eyJ2IjoiMSJ9.BmSwBRSGSpcnqMJc_UIMnnRobDBZOR88WyPXXGwk9_Yj_0J4cHFrEmg-0tz5hdxeSaZukuTwdJVmiRJZIxMoh-zjGXnM9JdjLqrrujLRUjreR_9vMKDeSRcRXeLgFOSf8mFeE_3Xu8czj8qY8f2TfAhNG2XByop6x77_2rqPaRqfsROCHRd5A_2k7KPOBCIhIXxKEVqVh0-LG22-B45IsN6QGdCPIQIpzKoY82n6oqo-S2izRUqVFSiDalDeSO9jrUIKeWaiy2ZFZgMm3gRItnrU1orT-nZh7kXs-cvACCw.AhaTmG8Iq8zHTNwMHnfayslCmYzOSgRpBVN7FGqbp2s&dib_tag=se&keywords=adidas%2Bshoes&qid=1766923990&sprefix=adidas%2Bsho%2Caps%2C170&sr=8-1&th=1&psc=1");
        testItem.setTargetPrice(100.0);
        testItem.setCurrentPrice(150.0);

        // 2. Save it to the H2 database
        repository.save(testItem);

        System.out.println("Successfully added: " + testItem.getName());
    }
}