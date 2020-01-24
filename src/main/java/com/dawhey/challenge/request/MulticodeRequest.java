package com.dawhey.challenge.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@Data
public class MulticodeRequest {

    private Map<String, String> cookies;

    private String verificationTokenValue;

    private char[] millekod;
}
