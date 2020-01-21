package com.dawhey.challenge.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Connection;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AccountPageCommandDataWrapper {

    private Map<String, String> cookies;

    private Connection.Response previousResponse;

}
