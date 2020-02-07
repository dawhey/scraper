package com.dawhey.challenge.step.output;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Session {

    public final Map<String, String> cookies;

    public void updateCookies(LoginStepOutput loginStepOutput) {
        cookies.putAll(loginStepOutput.response.cookies);
    }
}
