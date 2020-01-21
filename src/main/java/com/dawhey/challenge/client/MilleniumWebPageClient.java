package com.dawhey.challenge.client;


import com.dawhey.challenge.request.MulticodeRequestData;
import com.dawhey.challenge.request.PasswordRequestData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MilleniumWebPageClient {

    @Value("${millenium.millekod.form.parameter.name}")
    private String millekodFormParameterName;

    @Value("${millenium.password.form.parameter.name}")
    private String passwordFormParameterName;

    @Value("${millenium.security.digits.password.form.parameter.name}")
    private String securityDigitsPasswordFormParameterName;

    @Value("${millenium.request.verification.token.name}")
    private String verificationTokenName;

    @Value("${millenium.bot.detection.client.token.name}")
    private String botDetectionClientTokenName;

    @Value("${millenium.security.digits.view.model.login.challenge.parameter.name}")
    private String securityDigitsLoginChallengeParamName;

    @Value("${user.agent}")
    private String userAgent;

    @Value("${millenium.url.welcome-page}")
    private String milleniumBasePageUrl;

    @Value("${millenium.url.multicode-request.endpoint}")
    private String multicodeRequestEndpoint;

    @Value("${millenium.url.password-request.endpoint}")
    private String passwordRequestEndpoint;

    @Value("${millenium.url.account-list.endpoint}")
    private String accountListEndpoint;

    public Connection.Response getMilleniumWelcomePage() throws IOException {
        return Jsoup.connect(milleniumBasePageUrl + multicodeRequestEndpoint)
                .method(Connection.Method.GET)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();
    }

    public Connection.Response performMultiCodeRequest(final MulticodeRequestData requestData) throws IOException {
        return Jsoup.connect(milleniumBasePageUrl + multicodeRequestEndpoint)
                .method(Connection.Method.POST)
                .userAgent(userAgent)
                .cookies(requestData.getCookies())
                .data(millekodFormParameterName, String.valueOf(requestData.getMillekod()))
                .data(verificationTokenName, requestData.getVerificationTokenValue())
                .followRedirects(true)
                .execute();
    }

    public Connection.Response performPasswordRequest(final PasswordRequestData requestData) throws IOException {
        return Jsoup.connect(milleniumBasePageUrl + passwordRequestEndpoint)
                .method(Connection.Method.POST)
                .userAgent(userAgent)
                .cookies(requestData.getCookies())
                .data(passwordRequestFormStaticData())
                .data(requestData.getPeselFormData())
                .data(passwordFormParameterName, String.valueOf(requestData.getPassword()))
                .data(botDetectionClientTokenName, requestData.getBotDetectionClientToken())
                .data(verificationTokenName, requestData.getRequestVerificationToken())
                .data(securityDigitsLoginChallengeParamName, requestData.getSecurityDigitsLoginChallengeToken())
                .data(securityDigitsPasswordFormParameterName, requestData.getSecurityDigitsPassword())
                .followRedirects(true)
                .execute();
    }

    public Connection.Response getAccountListPage(final Connection.Response response, final Map<String, String> cookies) throws IOException {
        cookies.putAll(response.cookies());
        return Jsoup.connect(milleniumBasePageUrl + accountListEndpoint)
                .method(Connection.Method.GET)
                .userAgent(userAgent)
                .cookies(cookies)
                .followRedirects(true)
                .execute();
    }

    private Map<String, String> passwordRequestFormStaticData() {
        var formData = new HashMap<String, String>();
        formData.put("SecurityDigitsViewModel.LoginDocuments.DocumentType", "PESEL");
        formData.put("BotDetection.PresentedBefore", "False");
        return formData;
    }
}
