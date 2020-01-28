package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ResponseParser {

    public Document parse(Connection.Response response) {
        try {
            return response.parse();
        } catch (IOException e) {
            throw new RuntimeException("Error during parsing response to HTML document object.", e);
        }
    }
}
