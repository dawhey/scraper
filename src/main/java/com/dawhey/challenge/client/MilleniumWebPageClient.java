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

    public Connection.Response performMultiCodeRequest(MulticodeRequest request) {
        return execute(request(Connection.Method.POST, "osobiste2/Retail/Login/MulticodeRequest")
                .cookies(request.cookies)
                .data("Millekod", String.valueOf(request.millekod))
                .data(VERIFICATION_TOKEN_PARAM, request.verificationTokenValue));
    }

    public Connection.Response performPasswordRequest(PasswordRequest request) {
        return execute(request(Connection.Method.POST, "osobiste2/Retail/Login/PasswordOneRequest")
                .cookies(request.cookies)
                .data(passwordRequestFormStaticData())
                .data(request.peselFormData)
                .data("PasswordOne", String.valueOf(request.password))
                .data(BOT_DETECTION_TOKEN_PARAM, request.botDetectionClientToken)
                .data(VERIFICATION_TOKEN_PARAM, request.requestVerificationToken)
                .data(LOGIN_CHALLENGE_PARAM, request.securityDigitsLoginChallengeToken)
                .data("SecurityDigitsViewModel.LoginPassword", request.securityDigitsPassword));
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
