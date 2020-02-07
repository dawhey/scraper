package com.dawhey.challenge.model;

import com.dawhey.challenge.util.ScraperDocument;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Response {

    public final Map<String, String> cookies;

    public final ScraperDocument document;
}
