package com.elsa.price_tracker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

  
    public Double getPrice(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .timeout(10000)
                    .get();
    
            String html = doc.html();
            String priceText = "";
            if (url.contains("hollisterco.com")) {
               
                priceText = doc.select(".product-price-text, .price-sales").text();
            
                
                if (priceText.isEmpty()) {
                    priceText = doc.select("meta[property='og:price:amount']").attr("content");
                }
            }
            if (url.contains("ebay.com")) {
                priceText = doc.select(".x-price-primary").text();
    
  
                if (priceText.isEmpty() && html.contains("\"price\":[\"")) {
                    int start = html.indexOf("\"price\":[\"") + 10;
                    int end = html.indexOf("\"]", start);
                    priceText = html.substring(start, end);
                }
       
                if (priceText.isEmpty()) {
                    priceText = doc.select("meta[property='og:price:amount']").attr("content");
                }
            } else {
                priceText = doc.select(".price_color").text();
            }
    
            if (priceText != null && !priceText.isEmpty()) {
                String cleanPrice = priceText.replaceAll("[^0-9.]", "");
                return Double.parseDouble(cleanPrice);
            }
    
        } catch (Exception e) {
            System.out.println("Scrape failed for " + url + ": " + e.getMessage());
        }
        return null;
    }
}