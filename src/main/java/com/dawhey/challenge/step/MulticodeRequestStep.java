package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.springframework.stereotype.Component;

@Component
public class MulticodeRequestStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    private final ResponseParser responseParser;

    public MulticodeRequestStep(MilleniumWebPageClient milleniumWebPageClient, ResponseParser responseParser) {
        this.milleniumWebPageClient = milleniumWebPageClient;
        this.responseParser = responseParser;
    }

    public MulticodeRequestStepOutput execute(WelcomePageStepResultOutput output, Session session, char[] millekod) {
        var requestVerificationTokenValue = new ScraperDocument(responseParser, output.response)
                .findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM);

        var requestData = new MulticodeRequest(session.cookies, requestVerificationTokenValue, millekod);
        var response = milleniumWebPageClient.performMultiCodeRequest(requestData);
        return new MulticodeRequestStepOutput(response);
    }
}
