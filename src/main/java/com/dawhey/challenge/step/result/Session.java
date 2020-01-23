package com.dawhey.challenge.step.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Connection;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Session {

    private Map<String, String> cookies;

    private Connection.Response mostRecentResponse;

    public Session(Session session) {
        this.cookies = session.cookies;
        this.mostRecentResponse = session.mostRecentResponse;
    }
}
