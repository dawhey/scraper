package com.dawhey.challenge.request;

import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.util.ScraperDocument;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
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

    public static PasswordRequest request(ScraperDocument document, Map<String, String> cookies, char[] pesel, char[] password) {
        return new PasswordRequest(
                getPeselInputFormDataMap(document.findElementsBySelector("input[name~=PESEL*]"), pesel),
                document.findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM),
                document.findValueOfInputByName(RequestParams.BOT_DETECTION_TOKEN_PARAM),
                document.findValueOfInputByName(RequestParams.LOGIN_CHALLENGE_PARAM),
                new HashMap<>(cookies),
                password);
    }

    private static Map<String, String> getPeselInputFormDataMap(List<Element> peselInputList, char[] pesel) {
        return peselInputList.stream()
                .filter(element -> !element.attributes().hasKey("disabled"))
                .collect(Collectors.toMap(
                        element -> element.attr("name"),
                        element -> String.valueOf(pesel[peselChallengeDigitIndex(element)])
                ));
    }

    private static int peselChallengeDigitIndex(Element element) {
        var peselAttributeValue = element.attr("name");
        return Integer.parseInt(textAfter(peselAttributeValue, "PESEL_"));
    }

    private static String textAfter(String fullText, String after) {
        return fullText.substring(after.length());
    }
}
