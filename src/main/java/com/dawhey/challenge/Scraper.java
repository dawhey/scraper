package com.dawhey.challenge;

import com.dawhey.challenge.provider.CredentialsProvider;
import com.dawhey.challenge.service.ScrapingService;
import org.springframework.stereotype.Component;

@Component
public class Scraper {

    private final ScrapingService scrapingService;

    private final CredentialsProvider credentialsProvider;

    public Scraper(ScrapingService scrapingService, CredentialsProvider credentialsProvider) {
        this.scrapingService = scrapingService;
        this.credentialsProvider = credentialsProvider;
    }

    public void run() {
        var credentials = credentialsProvider.credentials();
        var accounts = scrapingService.scrapeBankPageForAccountDetails(credentials);
        System.out.println("Accounts list: " + accounts);
    }

}
