package com.dawhey.challenge.service;

import com.dawhey.challenge.exception.LoginFailureException;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.Connection;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginService {

    private final WelcomePageStep welcomePageStep;

    private final MulticodeRequestStep multicodeRequestStep;

    private final PasswordRequestStep passwordRequestStep;

    private final ResponseParser responseParser;

    public LoginService(WelcomePageStep welcomePageStep, MulticodeRequestStep multicodeRequestStep, PasswordRequestStep passwordRequestStep, ResponseParser responseParser) {
        this.welcomePageStep = welcomePageStep;
        this.multicodeRequestStep = multicodeRequestStep;
        this.passwordRequestStep = passwordRequestStep;
        this.responseParser = responseParser;
    }

    public Session login(Credentials credentials) {
        var session = new Session(new HashMap<>());

        var welcomePageStepOutput = welcomePageStep.execute();
        session.cookies.putAll(welcomePageStepOutput.response.cookies());

        var multicodeRequestStepOutput = multicodeRequestStep.execute(welcomePageStepOutput, session, credentials.millekod);
        session.cookies.putAll(multicodeRequestStepOutput.response.cookies());

        var response = passwordRequestStep.execute(multicodeRequestStepOutput, session, credentials.pesel, credentials.password);
        session.cookies.putAll(response.cookies());

        verifyIfUserLoggedIn(response);
        return session;
    }

    private void verifyIfUserLoggedIn(Connection.Response response) {
        if (!logoutButtonExist(new ScraperDocument(responseParser, response))) {
            throw new LoginFailureException("Failed to log in. Make sure you're passing the right credentials.");
        }
    }

    private boolean logoutButtonExist(ScraperDocument scraperDocument) {
        var logoutDivs = scraperDocument.findElementsBySelector("div[data-element-id~=logout]");
        return !logoutDivs.isEmpty();
    }
}
