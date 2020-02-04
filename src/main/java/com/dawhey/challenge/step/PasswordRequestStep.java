package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ResponseParser;
import org.springframework.stereotype.Component;

@Component
public class PasswordRequestStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    private final ResponseParser responseParser;

    public PasswordRequestStep(MilleniumWebPageClient milleniumWebPageClient, ResponseParser responseParser) {
        this.milleniumWebPageClient = milleniumWebPageClient;
        this.responseParser = responseParser;
    }

    public PasswordRequestStepOutput execute(MulticodeRequestStepOutput output, Session session, char[] pesel, char[] password) {
        var document = responseParser.parse(output.response);
        var request = PasswordRequest.request(document, session.cookies, pesel, password);
        return new PasswordRequestStepOutput(milleniumWebPageClient.performPasswordRequest(request));
    }


}
