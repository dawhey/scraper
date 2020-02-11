package com.dawhey.challenge.web.request;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.dawhey.challenge.web.client.RequestParams.*;

@AllArgsConstructor
public class PasswordRequestPayload implements Payload {

    public final Map<String, String> peselFormData;

    public final String requestVerificationToken;

    public final String botDetectionClientToken;

    public final String securityDigitsLoginChallengeToken;

    public final char[] password;

    public final String securityDigitsPassword;

    @Override
    public Map<String, String> getParameters() {
        var parameters = new HashMap<String, String>();
        parameters.putAll(peselFormData);
        parameters.putAll(Map.of(
                "PasswordOne", String.valueOf(password),
                BOT_DETECTION_TOKEN_PARAM, botDetectionClientToken,
                VERIFICATION_TOKEN_PARAM, requestVerificationToken,
                LOGIN_CHALLENGE_PARAM, securityDigitsLoginChallengeToken,
                "SecurityDigitsViewModel.LoginPassword", securityDigitsPassword,
                "SecurityDigitsViewModel.LoginDocuments.DocumentType", "PESEL",
                "BotDetection.PresentedBefore", "False"
        ));
        return parameters;
    }
}
