package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.springframework.stereotype.Component;

@Component
public class PasswordRequestStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    private ResponseParser responseParser = new ResponseParser();

    public PasswordRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public PasswordRequestStepOutput execute(MulticodeRequestStepOutput output, Session session, char[] pesel, char[] password) {
        var document = new ScraperDocument(responseParser, output.response);
        var request = PasswordRequest.request(document, session, pesel, password);
        var response = milleniumWebPageClient.performPasswordRequest(request);
        return new PasswordRequestStepOutput(response);
    }


}
