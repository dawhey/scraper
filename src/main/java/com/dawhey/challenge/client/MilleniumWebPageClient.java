package com.dawhey.challenge.client;


import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.dawhey.challenge.client.RequestParams.*;

@Service
public class MilleniumWebPageClient {

    public Connection.Response getMilleniumWelcomePage() {
        return execute(request(Connection.Method.GET, "osobiste2/Retail/Login/MulticodeRequest"));
    }

    public Connection.Response performMultiCodeRequest(MulticodeRequest requestData) {
        return execute(request(Connection.Method.POST, "osobiste2/Retail/Login/MulticodeRequest")
                .cookies(requestData.cookies)
                .data("Millekod", String.valueOf(requestData.millekod))
                .data(VERIFICATION_TOKEN_PARAM, requestData.verificationTokenValue));
    }

    public Connection.Response performPasswordRequest(PasswordRequest requestData) {
        return execute(request(Connection.Method.POST, "osobiste2/Retail/Login/PasswordOneRequest")
                .cookies(requestData.cookies)
                .data(passwordRequestFormStaticData())
                .data(requestData.peselFormData)
                .data("PasswordOne", String.valueOf(requestData.password))
                .data(BOT_DETECTION_TOKEN_PARAM, requestData.botDetectionClientToken)
                .data(VERIFICATION_TOKEN_PARAM, requestData.requestVerificationToken)
                .data(LOGIN_CHALLENGE_PARAM, requestData.securityDigitsLoginChallengeToken)
                .data("SecurityDigitsViewModel.LoginPassword", requestData.getSecurityDigitsPassword()));
    }

    public Connection.Response getAccountListPage(Map<String, String> cookies) {
        return execute(request(Connection.Method.GET, "osobiste2/Accounts/CurrentAccountsList/List")
                .cookies(cookies));
    }

    private Connection request(Connection.Method method, String endpoint) {
        return Jsoup.connect(MILLENIUM_BASE_URL + endpoint)
                .method(method)
                .userAgent(USER_AGENT)
                .followRedirects(true);
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
