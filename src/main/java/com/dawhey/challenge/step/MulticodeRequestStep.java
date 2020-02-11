package com.dawhey.challenge.step;

import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.web.client.MilleniumWebPageClient;
import com.dawhey.challenge.web.request.MulticodeRequestPayload;
import org.springframework.stereotype.Component;

import static com.dawhey.challenge.web.client.RequestParams.VERIFICATION_TOKEN_PARAM;

@Component
public class MulticodeRequestStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public MulticodeRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public MulticodeRequestStepOutput execute(WelcomePageStepResultOutput output, Session session, char[] millekod) {
        var requestVerificationTokenValue = output.response.document.findValueOfInputByName(VERIFICATION_TOKEN_PARAM);
        var payload = new MulticodeRequestPayload(requestVerificationTokenValue, millekod);
        var response = milleniumWebPageClient.performMultiCodeRequest(payload, session.cookies);
        return new MulticodeRequestStepOutput(response);
    }
}
