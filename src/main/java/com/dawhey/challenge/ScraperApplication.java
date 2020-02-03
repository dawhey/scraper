package com.dawhey.challenge;

import com.dawhey.challenge.provider.SpringPropertiesCredentialsProvider;
import com.dawhey.challenge.service.ScrapingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ScraperApplication {

    private static ScrapingService scrapingService;

    private static SpringPropertiesCredentialsProvider springPropertiesCredentialsProvider;

    public static void main(String[] args) {
        setUp(args);
        scrape();
    }

    private static ConfigurableApplicationContext setUp(String[] args) {
        var context = SpringApplication.run(ScraperApplication.class, args);
        scrapingService = context.getBean(ScrapingService.class);
        springPropertiesCredentialsProvider = new SpringPropertiesCredentialsProvider();
        return context;
    }

    private static void scrape() {
        var credentials = springPropertiesCredentialsProvider.credentials();
        var accounts = scrapingService.scrapeBankPageForAccountDetails(credentials);
        System.out.println("Accounts list: " + accounts);
    }
}
