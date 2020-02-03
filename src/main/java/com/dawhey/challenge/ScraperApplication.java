package com.dawhey.challenge;

import com.dawhey.challenge.provider.CredentialsProvider;
import com.dawhey.challenge.service.ScrapingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScraperApplication {

    private static ScrapingService scrapingService;

    private static CredentialsProvider credentialsProvider;

    public static void main(String[] args) {
        setUp(args);
        scrape();
    }

    private static void setUp(String[] args) {
        var context = SpringApplication.run(ScraperApplication.class, args);
        scrapingService = context.getBean(ScrapingService.class);
        credentialsProvider = new CredentialsProvider();
    }

    private static void scrape() {
        var credentials = credentialsProvider.credentials();
        var accounts = scrapingService.scrapeBankPageForAccountDetails(credentials);
        System.out.println("Accounts list: " + accounts);
    }
}
