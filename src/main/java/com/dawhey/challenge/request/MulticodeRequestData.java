package com.dawhey.challenge.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MulticodeRequestData {

    private Map<String, String> cookies;

    private String verificationTokenValue;

    private char[] millekod;
}
