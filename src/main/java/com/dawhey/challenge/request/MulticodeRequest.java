package com.dawhey.challenge.request;

import lombok.*;

import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
public class MulticodeRequest {

    public final Map<String, String> cookies;

    public final String verificationTokenValue;

    public final char[] millekod;
}
