package com.dawhey.challenge.request;

import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class PasswordRequest {

    public final Map<String, String> peselFormData;

    public final String requestVerificationToken;

    public final String botDetectionClientToken;

    public final String securityDigitsLoginChallengeToken;

    public final Map<String, String> cookies;

    public final char[] password;

    public final String securityDigitsPassword;

    public PasswordRequest(
            Map<String, String> peselFormData,
            String requestVerificationToken,
            String botDetectionClientToken,
            String securityDigitsLoginChallengeToken,
            Map<String, String> cookies,
            char[] password) {
        this.peselFormData = peselFormData;
        this.requestVerificationToken = requestVerificationToken;
        this.botDetectionClientToken = botDetectionClientToken;
        this.securityDigitsLoginChallengeToken = securityDigitsLoginChallengeToken;
        this.cookies = cookies;
        this.password = password;
        this.securityDigitsPassword = String.join(Strings.EMPTY, peselFormData.values());
    }
}
