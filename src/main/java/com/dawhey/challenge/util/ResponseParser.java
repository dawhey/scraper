package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseParser {

    public Document parse(Connection.Response response) {
        try {
            return response.parse();
        } catch (IOException e) {
            throw new RuntimeException("Error during parsing response to HTML document object.", e);
        }
    }
}
