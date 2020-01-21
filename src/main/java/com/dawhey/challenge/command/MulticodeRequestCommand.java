package com.dawhey.challenge.command;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequestData;
import com.dawhey.challenge.util.DocumentHandler;
import com.dawhey.challenge.wrapper.MulticodeRequestCommandDataWrapper;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MulticodeRequestCommand extends ScrapingCommand<MulticodeRequestCommandDataWrapper, Connection.Response> {

    @Value("${millenium.request.verification.token.name}")
    private String requestVerificationTokenName;

    private final MilleniumWebPageClient milleniumWebPageClient;

    public MulticodeRequestCommand(final MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    @Override
    protected Connection.Response executeLogic(final MulticodeRequestCommandDataWrapper commandData) throws Exception {
        final String requestVerificationTokenValue = new DocumentHandler(commandData.getResponse().parse()).findValueOfInputByName(requestVerificationTokenName);
        final MulticodeRequestData requestData = buildRequestData(commandData.getResponse().cookies(), requestVerificationTokenValue, commandData.getMillekod());
        return milleniumWebPageClient.performMultiCodeRequest(requestData);
    }

    @Override
    protected String name() {
        return "Multicode Request Command";
    }

    private MulticodeRequestData buildRequestData(final Map<String, String> cookies, final String requestVerificationToken, final char[] millekod) {
        return MulticodeRequestData.builder()
                .cookies(cookies)
                .verificationTokenValue(requestVerificationToken)
                .millekod(millekod)
                .build();
    }
}
