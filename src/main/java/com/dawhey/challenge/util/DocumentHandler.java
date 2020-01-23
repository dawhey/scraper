package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class DocumentHandler {

    private Document document;

    public DocumentHandler(Connection.Response response) {
        try {
            this.document = response.parse();
        } catch (IOException e) {
            throw new RuntimeException("Error during parsing response to HTML document object.", e);
        }
    }

    public String findValueOfInputByName(String inputName) {
        return document
                .select("input[name=" + inputName + "]")
                .attr("value");
    }

    public List<Element> findElementsBySelector(String selector) {
        return document
                .select(selector);
    }

    public List<Element> findElementsByClass(String cssClass) {
        return document
                .getElementsByClass(cssClass);
    }
}
