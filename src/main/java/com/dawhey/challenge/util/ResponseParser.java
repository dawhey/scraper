package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseParser {

    public ScraperDocument parse(Connection.Response response) {
        try {
            return new ScraperDocument(response.parse());
        } catch (IOException e) {
            throw new RuntimeException("Error during parsing response to HTML document object.", e);
        }
    }
}
