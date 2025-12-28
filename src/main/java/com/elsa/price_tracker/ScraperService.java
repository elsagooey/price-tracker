package com.elsa.price_tracker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

    // Inside ScraperService.java
public Double getPrice(String url) {
    try {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get();

        // CHANGE THIS LINE: .price -> .price_color
        String priceText = doc.select(".price_color").text();

        if (priceText == null || priceText.isEmpty()) {
            System.out.println("Could not find price element on page.");
            return null;
        }

        // Clean text: remove the currency symbol (£) and keep only numbers/dots
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
        
    } catch (Exception e) {
        System.out.println("Error scraping: " + e.getMessage());
        return null;
    }
}
}