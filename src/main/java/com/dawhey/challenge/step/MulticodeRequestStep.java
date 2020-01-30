package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.util.DocumentParser;
import com.dawhey.challenge.util.ResponseParser;
import org.springframework.stereotype.Component;

@Component
public class MulticodeRequestStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    private ResponseParser responseParser = new ResponseParser();

    public MulticodeRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public MulticodeRequestStepOutput execute(WelcomePageStepResultOutput output, Session session, char[] millekod) {
        var requestVerificationTokenValue = new DocumentParser(responseParser, output.response)
                .findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM);

        var requestData = new MulticodeRequest(session.cookies, requestVerificationTokenValue, millekod);
        var response = milleniumWebPageClient.performMultiCodeRequest(requestData);
        return new MulticodeRequestStepOutput(response);
    }
}
