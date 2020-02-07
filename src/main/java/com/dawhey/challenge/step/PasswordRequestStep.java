package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dawhey.challenge.client.RequestParams.*;

@Component
public class PasswordRequestStep {

    private final MilleniumWebPageClient milleniumWebPageClient;

    public PasswordRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public PasswordRequestStepOutput execute(MulticodeRequestStepOutput output, Session session, char[] pesel, char[] password) {
        var request = request(output.response.document, session.cookies, pesel, password);
        return new PasswordRequestStepOutput(milleniumWebPageClient.performPasswordRequest(request));
    }

    public static PasswordRequest request(ScraperDocument document, Map<String, String> cookies, char[] pesel, char[] password) {
        return new PasswordRequest(
                getPeselInputFormDataMap(document.findElementsBySelector("input[name~=PESEL*]"), pesel),
                document.findValueOfInputByName(VERIFICATION_TOKEN_PARAM),
                document.findValueOfInputByName(BOT_DETECTION_TOKEN_PARAM),
                document.findValueOfInputByName(LOGIN_CHALLENGE_PARAM),
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
