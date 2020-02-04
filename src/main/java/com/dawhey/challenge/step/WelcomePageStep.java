package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import org.springframework.stereotype.Component;

@Component
public class WelcomePageStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public WelcomePageStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public WelcomePageStepResultOutput execute() {
        var response = milleniumWebPageClient.getMilleniumWelcomePage();
        return new WelcomePageStepResultOutput(response);
    }
}
