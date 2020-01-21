package com.dawhey.challenge.service;

import com.dawhey.challenge.exception.RequestFailureException;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResponseValidatingService {

    @Value("${millenium.url.welcome-page}")
    private String milleniumBasePageUrl;

    @Value("${millenium.url.password-request.endpoint}")
    private String passwordRequestEndpoint;

    @Value("${millenium.url.account-page.endpoint}")
    private String accountPageEndpoint;

    public void validateMulticodeRequestResponse(final Connection.Response response) {
        validateRequestResponse(response, milleniumBasePageUrl + passwordRequestEndpoint);
    }

    public void validatePasswordRequestResponse(final Connection.Response response) {
        validateRequestResponse(response, milleniumBasePageUrl + accountPageEndpoint);
    }

    private void validateRequestResponse(final Connection.Response response, final String expected) {
        if (response.url().toString().equals(expected)) {
            System.out.println("Reached: " + response.url());
        } else {
            throw new RequestFailureException("Wrong response URL. Actual: " + response.url() + ", expected: " + expected);
        }
    }
}
