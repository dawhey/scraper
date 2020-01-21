package com.dawhey.challenge;

import com.dawhey.challenge.exception.RequestFailureException;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.service.ScrapingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Set;

@SpringBootApplication
public class ChallengeApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(ChallengeApplication.class, args);
        final ScrapingService scrapingService = context.getBean(ScrapingService.class);

        final char[] millekod = context.getEnvironment().getProperty("millenium.millekod").toCharArray();
        final char[] password = context.getEnvironment().getProperty("millenium.password").toCharArray();
        final char[] pesel = context.getEnvironment().getProperty("millenium.pesel").toCharArray();

        try {
            final Set<Account> accounts = scrapingService.scrapBankPageForAccountDetails(millekod, pesel, password);
            System.out.println("Accounts list: " + accounts);
        } catch (RequestFailureException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        } finally {
            System.out.println("Finished...");
        }
    }
}
