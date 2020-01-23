package com.dawhey.challenge.step.result;

import org.jsoup.Connection;

import java.util.Map;

public class WelcomePageStepResultSession extends Session {

    public WelcomePageStepResultSession(Map<String, String> cookies, Connection.Response mostRecentResponse) {
        super(cookies, mostRecentResponse);
    }
}
