package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import org.springframework.stereotype.Component;

import static com.dawhey.challenge.client.RequestParams.VERIFICATION_TOKEN_PARAM;

@Component
public class MulticodeRequestStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public MulticodeRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public MulticodeRequestStepOutput execute(WelcomePageStepResultOutput output, Session session, char[] millekod) {
        var requestVerificationTokenValue = output.response.document.findValueOfInputByName(VERIFICATION_TOKEN_PARAM);
        var requestData = new MulticodeRequest(session.cookies, requestVerificationTokenValue, millekod);
        var response = milleniumWebPageClient.performMultiCodeRequest(requestData);
        return new MulticodeRequestStepOutput(response);
    }
}
