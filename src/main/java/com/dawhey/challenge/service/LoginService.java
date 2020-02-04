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

        var welcomePageOutput = welcomePageStep.execute();
        session.updateCookies(welcomePageOutput);

        var multicodeOutput = multicodeRequestStep.execute(welcomePageOutput, session, credentials.millekod);
        session.updateCookies(multicodeOutput);

        var passwordOutput = passwordRequestStep.execute(multicodeOutput, session, credentials.pesel, credentials.password);
        session.updateCookies(passwordOutput);

        verifyIfUserLoggedIn(passwordOutput.response);
        return session;
    }

    private void verifyIfUserLoggedIn(Connection.Response response) {
        var document = responseParser.parse(response);
        if (!logoutButtonExist(document)) {
            throw new LoginFailureException("Failed to log in. Make sure you're passing the right credentials.");
        }
    }

    private static boolean logoutButtonExist(ScraperDocument scraperDocument) {
        var logoutDivs = scraperDocument.findElementsBySelector("div[data-element-id~=logout]");
        return !logoutDivs.isEmpty();
    }
}
