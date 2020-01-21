package com.dawhey.challenge.command;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class WelcomePageCommand extends ScrapingCommand<Optional<Object>, Connection.Response> {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public WelcomePageCommand(final MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    @Override
    protected Connection.Response executeLogic(Optional<Object> arg) throws IOException {
        return milleniumWebPageClient.getMilleniumWelcomePage();
    }

    @Override
    protected String name() {
        return "Welcome Page Command";
    }
}
