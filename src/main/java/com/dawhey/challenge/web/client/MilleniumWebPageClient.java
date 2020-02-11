package com.dawhey.challenge.web.client;

import com.dawhey.challenge.web.parser.ResponseParser;
import com.dawhey.challenge.web.request.MulticodeRequestPayload;
import com.dawhey.challenge.web.request.PasswordRequestPayload;
import com.dawhey.challenge.web.request.Payload;
import com.dawhey.challenge.web.response.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

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

    public Response performMultiCodeRequest(MulticodeRequestPayload payload, Map<String, String> cookies) {
        return fetchAndParse(request(Connection.Method.POST, "osobiste2/Retail/Login/MulticodeRequest", payload, cookies));
    }

    public Response performPasswordRequest(PasswordRequestPayload payload, Map<String, String> cookies) {
        return fetchAndParse(request(Connection.Method.POST, "osobiste2/Retail/Login/PasswordOneRequest", payload, cookies));
    }

    public Response getAccountListPage(Map<String, String> cookies) {
        return fetchAndParse(request(Connection.Method.GET, "osobiste2/Accounts/CurrentAccountsList/List").cookies(cookies));
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

    private static Connection request(Connection.Method method, String path, Payload payload, Map<String, String> cookies) {
        return request(method, path)
                .cookies(cookies)
                .data(payload.getParameters());
    }
}
