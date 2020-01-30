package com.dawhey.challenge.service;

import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.step.AccountPageStep;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.Session;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScrapingService {

    private final WelcomePageStep welcomePageStep;

    private final MulticodeRequestStep multicodeRequestStep;

    private final PasswordRequestStep passwordRequestStep;

    private final AccountPageStep accountPageStep;

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
        var welcomePageStepOutput = welcomePageStep.execute();
        var session = new Session(welcomePageStepOutput.response.cookies());

        var multicodeRequestStepOutput = multicodeRequestStep.execute(welcomePageStepOutput, session, credentials.millekod);
        session.cookies.putAll(multicodeRequestStepOutput.response.cookies());

        var passwordRequestStepOutput = passwordRequestStep.execute(multicodeRequestStepOutput, session, credentials.pesel, credentials.password);
        session.cookies.putAll(passwordRequestStepOutput.response.cookies());

        return accountPageStep.execute(passwordRequestStepOutput, session);
    }
}
