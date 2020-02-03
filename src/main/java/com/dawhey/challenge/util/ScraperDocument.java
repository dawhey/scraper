package com.dawhey.challenge.util;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class ScraperDocument {

    private final Document document;

    public ScraperDocument(ResponseParser parser, Connection.Response response) {
        this.document = parser.parse(response);
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