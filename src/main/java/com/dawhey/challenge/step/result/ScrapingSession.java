package com.dawhey.challenge.step.result;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ScrapingSession {

    private final Map<String, String> cookies = new HashMap<>();

    public Map<String, String> cookies() {
        return cookies;
    }

    public void putCookies(Map<String, String> cookies) {
        this.cookies.putAll(cookies);
    }
}
