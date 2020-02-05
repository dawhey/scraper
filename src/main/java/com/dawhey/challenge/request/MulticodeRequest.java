package com.dawhey.challenge.request;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class MulticodeRequest {

    public final Map<String, String> cookies;

    public final String verificationTokenValue;

    public final char[] millekod;
}
