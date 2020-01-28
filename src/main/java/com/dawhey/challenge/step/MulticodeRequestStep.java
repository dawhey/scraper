package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.result.MulticodeRequestStepResultSession;
import com.dawhey.challenge.step.result.WelcomePageStepResultSession;
import com.dawhey.challenge.util.DocumentParser;
import com.dawhey.challenge.util.ResponseParser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MulticodeRequestStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    private ResponseParser responseParser = new ResponseParser();

    public MulticodeRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public MulticodeRequestStepResultSession execute(WelcomePageStepResultSession session, char[] millekod) {
        var requestVerificationTokenValue = new DocumentParser(responseParser, session.getMostRecentResponse())
                .findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM);

        var requestData = buildRequestData(session.getCookies(), requestVerificationTokenValue, millekod);
        session.setMostRecentResponse(milleniumWebPageClient.performMultiCodeRequest(requestData));
        return new MulticodeRequestStepResultSession(session);
    }

    private MulticodeRequest buildRequestData(Map<String, String> cookies, String requestVerificationToken, char[] millekod) {
        return MulticodeRequest.builder()
                .cookies(cookies)
                .verificationTokenValue(requestVerificationToken)
                .millekod(millekod)
                .build();
    }
}
