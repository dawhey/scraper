package com.dawhey.challenge.web.response;

import com.dawhey.challenge.web.parser.ScraperDocument;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Response {

    public final Map<String, String> cookies;

    public final ScraperDocument document;
}
