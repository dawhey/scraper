package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.result.MulticodeRequestStepResultSession;
import com.dawhey.challenge.step.result.PasswordRequestStepResultSession;
import com.dawhey.challenge.util.DocumentHandler;
import com.dawhey.challenge.util.DocumentParser;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PasswordRequestStep {

    /**
     * Index of first character of number in PESEL input name
     */
    public static final int PESEL_INPUT_NUMBER_INDEX = 6;

    private MilleniumWebPageClient milleniumWebPageClient;

    private DocumentParser documentParser = new DocumentParser();

    public PasswordRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public PasswordRequestStepResultSession execute(MulticodeRequestStepResultSession session, char[] pesel, char[] password) {
        var request = buildRequestData(session, pesel, password);
        var response = milleniumWebPageClient.performPasswordRequest(request);
        session.setMostRecentResponse(response);
        session.getCookies().putAll(response.cookies());
        return new PasswordRequestStepResultSession(session);
    }

    private PasswordRequest buildRequestData(MulticodeRequestStepResultSession session, char[] pesel, char[] password) {
        var documentHandler = new DocumentHandler(documentParser.parseFrom(session.getMostRecentResponse()));

        return PasswordRequest.builder()
                .peselFormData(getPeselInputFormDataMap(documentHandler.findElementsBySelector("input[name~=PESEL*]"), pesel))
                .botDetectionClientToken(documentHandler.findValueOfInputByName(RequestParams.BOT_DETECTION_TOKEN_PARAM))
                .requestVerificationToken(documentHandler.findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM))
                .securityDigitsLoginChallengeToken(documentHandler.findValueOfInputByName(RequestParams.LOGIN_CHALLENGE_PARAM))
                .cookies(new HashMap<>(session.getCookies()))
                .password(password)
                .build();
    }

    /**
     * @param peselInputList all pesel number inputs (disabled and enabled)
     * @return Map<PESEL_DIGIT_INDEX, PESEL_VALUE_AT_INDEX> - map of form data that should be filled in by user eg. (("PESEL_6", "2"), ("PESEL_10", "9"));
     */
    private Map<String, String> getPeselInputFormDataMap(List<Element> peselInputList, char[] pesel) {
        return peselInputList.stream()
                .filter(element -> !element.attributes().hasKey("disabled"))
                .collect(Collectors.toMap(
                        element -> element.attr("name"),
                        element -> String.valueOf(pesel[Integer.parseInt(element.attr("name").substring(PESEL_INPUT_NUMBER_INDEX))])));
    }
}
