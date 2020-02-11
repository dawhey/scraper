package com.dawhey.challenge.web.request;

import lombok.AllArgsConstructor;

import java.util.Map;

import static com.dawhey.challenge.web.client.RequestParams.VERIFICATION_TOKEN_PARAM;

@AllArgsConstructor
public class MulticodeRequestPayload implements Payload {

    public final String verificationTokenValue;

    public final char[] millekod;

    @Override
    public Map<String, String> getParameters() {
        return Map.of(
                "Millekod", String.valueOf(millekod),
                VERIFICATION_TOKEN_PARAM, verificationTokenValue
        );
    }
}
