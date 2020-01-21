package com.dawhey.challenge.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Connection;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PasswordRequestCommandDataWrapper {

    private Connection.Response previousResponse;

    private Map<String, String> cookies;

    private char[] pesel;

    private char[] password;
}
