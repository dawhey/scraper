package com.dawhey.challenge.request;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
public class PasswordRequest {

    public final Map<String, String> peselFormData;

    public final String requestVerificationToken;

    public final String botDetectionClientToken;

    public final String securityDigitsLoginChallengeToken;

    public final Map<String, String> cookies;

    public final char[] password;

    public String getSecurityDigitsPassword() {
        return String.join(Strings.EMPTY, peselFormData.values());
    }
}
