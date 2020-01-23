package com.dawhey.challenge.request;

import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

@Getter
@Builder
public class PasswordRequest {

    private Map<String, String> peselFormData;

    private String requestVerificationToken;

    private String botDetectionClientToken;

    private String securityDigitsLoginChallengeToken;

    private Map<String, String> cookies;

    private char[] password;

    public String getSecurityDigitsPassword() {
        return String.join(Strings.EMPTY, peselFormData.values());
    }
}
