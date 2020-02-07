package com.dawhey.challenge.client;

import com.dawhey.challenge.model.Response;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.util.ResponseParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.dawhey.challenge.client.RequestParams.*;

@Service
public class MilleniumWebPageClient {

    private static final String MILLENIUM_BASE_URL = "https://www.bankmillennium.pl/";

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) snap Chromium/79.0.3945.117 Chrome/79.0.3945.117 Safari/537.36";

    private final ResponseParser responseParser;

    public MilleniumWebPageClient(ResponseParser responseParser) {
        this.responseParser = responseParser;
    }

    public Response getMilleniumWelcomePage() {
        return fetchAndParse(request(Connection.Method.GET, "osobiste2/Retail/Login/MulticodeRequest"));
    }

    public Response performMultiCodeRequest(MulticodeRequest request) {
        return fetchAndParse(request(Connection.Method.POST, "osobiste2/Retail/Login/MulticodeRequest")
                .cookies(request.cookies)
                .data("Millekod", String.valueOf(request.millekod))
                .data(VERIFICATION_TOKEN_PARAM, request.verificationTokenValue));
    }

    public Response performPasswordRequest(PasswordRequest request) {
        return fetchAndParse(request(Connection.Method.POST, "osobiste2/Retail/Login/PasswordOneRequest")
                .cookies(request.cookies)
                .data(passwordRequestFormStaticData())
                .data(request.peselFormData)
                .data("PasswordOne", String.valueOf(request.password))
                .data(BOT_DETECTION_TOKEN_PARAM, request.botDetectionClientToken)
                .data(VERIFICATION_TOKEN_PARAM, request.requestVerificationToken)
                .data(LOGIN_CHALLENGE_PARAM, request.securityDigitsLoginChallengeToken)
                .data("SecurityDigitsViewModel.LoginPassword", request.securityDigitsPassword));
    }

    public Response getAccountListPage(Map<String, String> cookies) {
        return fetchAndParse(request(Connection.Method.GET, "osobiste2/Accounts/CurrentAccountsList/List")
                .cookies(cookies));
    }

    private Response fetchAndParse(Connection connection) {
        try {
            var jsoupResponse = connection.execute();
            var document = responseParser.parse(jsoupResponse);
            return new Response(jsoupResponse.cookies(), document);
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute connection to url: " + connection.request().url(), e);
        }
    }

    private static Connection request(Connection.Method method, String path) {
        return Jsoup.connect(MILLENIUM_BASE_URL + path)
                .method(method)
                .userAgent(USER_AGENT)
                .followRedirects(true);
    }

    private static Map<String, String> passwordRequestFormStaticData() {
        var formData = new HashMap<String, String>();
        formData.put("SecurityDigitsViewModel.LoginDocuments.DocumentType", "PESEL");
        formData.put("BotDetection.PresentedBefore", "False");
        return formData;
    }
}
