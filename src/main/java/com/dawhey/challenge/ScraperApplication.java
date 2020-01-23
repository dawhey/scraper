package com.dawhey.challenge;

import com.dawhey.challenge.provider.SpringPropertiesCredentialsProvider;
import com.dawhey.challenge.service.ScrapingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScraperApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(ScraperApplication.class, args);
        var scrapingService = context.getBean(ScrapingService.class);
        var credentials = new SpringPropertiesCredentialsProvider().getFrom(context);

        var accounts = scrapingService.scrapeBankPageForAccountDetails(credentials);
        System.out.println("Accounts list: " + accounts);
    }
}
