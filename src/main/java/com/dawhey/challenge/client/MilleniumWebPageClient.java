package com.dawhey.challenge.client;


import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MilleniumWebPageClient {

    @Value("${millenium.base.url}")
    private String milleniumBaseUrl;

    public Connection.Response getMilleniumWelcomePage() {
        return execute(Jsoup.connect(milleniumBaseUrl + "osobiste2/Retail/Login/MulticodeRequest")
                .method(Connection.Method.GET)
                .userAgent(RequestParams.USER_AGENT)
                .followRedirects(true));
    }

    public Connection.Response performMultiCodeRequest(MulticodeRequest requestData) {
        return execute(Jsoup.connect(milleniumBaseUrl + "osobiste2/Retail/Login/MulticodeRequest")
                .method(Connection.Method.POST)
                .userAgent(RequestParams.USER_AGENT)
                .cookies(requestData.cookies)
                .data("Millekod", String.valueOf(requestData.millekod))
                .data(RequestParams.VERIFICATION_TOKEN_PARAM, requestData.verificationTokenValue)
                .followRedirects(true));
    }

    public Connection.Response performPasswordRequest(PasswordRequest requestData) {
        return execute(Jsoup.connect(milleniumBaseUrl + "osobiste2/Retail/Login/PasswordOneRequest")
                .method(Connection.Method.POST)
                .userAgent(RequestParams.USER_AGENT)
                .cookies(requestData.cookies)
                .data(passwordRequestFormStaticData())
                .data(requestData.peselFormData)
                .data("PasswordOne", String.valueOf(requestData.password))
                .data(RequestParams.BOT_DETECTION_TOKEN_PARAM, requestData.botDetectionClientToken)
                .data(RequestParams.VERIFICATION_TOKEN_PARAM, requestData.requestVerificationToken)
                .data(RequestParams.LOGIN_CHALLENGE_PARAM, requestData.securityDigitsLoginChallengeToken)
                .data("SecurityDigitsViewModel.LoginPassword", requestData.getSecurityDigitsPassword())
                .followRedirects(true));
    }

    public Connection.Response getAccountListPage(Map<String, String> cookies) {
        return execute(Jsoup.connect(milleniumBaseUrl + "osobiste2/Accounts/CurrentAccountsList/List")
                .method(Connection.Method.GET)
                .userAgent(RequestParams.USER_AGENT)
                .cookies(cookies)
                .followRedirects(true));
    }

    private Connection.Response execute(Connection connection) {
        try {
            return connection.execute();
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute connection to url: " + connection.request().url(), e);
        }
    }

    private Map<String, String> passwordRequestFormStaticData() {
        var formData = new HashMap<String, String>();
        formData.put("SecurityDigitsViewModel.LoginDocuments.DocumentType", "PESEL");
        formData.put("BotDetection.PresentedBefore", "False");
        return formData;
    }
}
