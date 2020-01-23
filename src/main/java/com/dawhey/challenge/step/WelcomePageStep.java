package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.step.result.WelcomePageStepResultSession;
import org.springframework.stereotype.Component;

@Component
public class WelcomePageStep {

    private MilleniumWebPageClient milleniumWebPageClient;

    public WelcomePageStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public WelcomePageStepResultSession execute() {
        var response = milleniumWebPageClient.getMilleniumWelcomePage();
        return new WelcomePageStepResultSession(response.cookies(), response);
    }
}
