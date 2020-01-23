package com.dawhey.challenge.service;

import com.dawhey.challenge.step.AccountPageStep;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScrapingService {

    private WelcomePageStep welcomePageStep;

    private MulticodeRequestStep multicodeRequestStep;

    private PasswordRequestStep passwordRequestStep;

    private AccountPageStep accountPageStep;

    public ScrapingService(WelcomePageStep welcomePageStep,
                           MulticodeRequestStep multicodeRequestStep,
                           PasswordRequestStep passwordRequestStep,
                           AccountPageStep accountPageStep) {
        this.welcomePageStep = welcomePageStep;
        this.multicodeRequestStep = multicodeRequestStep;
        this.passwordRequestStep = passwordRequestStep;
        this.accountPageStep = accountPageStep;
    }

    public Set<Account> scrapeBankPageForAccountDetails(Credentials credentials) {
        var welcomePageStepResultSession = welcomePageStep.execute();
        var multicodeRequestStepResultSession = multicodeRequestStep.execute(welcomePageStepResultSession ,credentials.getMillekod());
        var passwordRequestStepResultSession = passwordRequestStep.execute(multicodeRequestStepResultSession, credentials.getPesel(), credentials.getPassword());
        return accountPageStep.execute(passwordRequestStepResultSession);
    }
}
