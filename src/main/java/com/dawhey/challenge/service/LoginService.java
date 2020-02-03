package com.dawhey.challenge.service;

import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.exception.InvalidCredentialsException;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.Session;
import org.jsoup.Connection;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginService {

    private final WelcomePageStep welcomePageStep;

    private final MulticodeRequestStep multicodeRequestStep;

    private final PasswordRequestStep passwordRequestStep;

    public LoginService(WelcomePageStep welcomePageStep, MulticodeRequestStep multicodeRequestStep, PasswordRequestStep passwordRequestStep) {
        this.welcomePageStep = welcomePageStep;
        this.multicodeRequestStep = multicodeRequestStep;
        this.passwordRequestStep = passwordRequestStep;
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

    private void verifyIfUserLoggedIn(Connection.Response previousResponse) {
        if (!previousResponse.url().toString().equals(RequestParams.MILLENIUM_BASE_URL + "osobiste/")) {
            throw new InvalidCredentialsException("Invalid credentials: failed to log in...");
        }
    }
}
