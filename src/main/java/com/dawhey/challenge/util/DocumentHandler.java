package com.dawhey.challenge.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class DocumentHandler {

    private Document document;

    public DocumentHandler(Document document) {
        this.document = document;
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
