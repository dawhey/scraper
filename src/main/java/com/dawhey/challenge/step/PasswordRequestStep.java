package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.DocumentParser;
import com.dawhey.challenge.util.ResponseParser;
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

    private ResponseParser responseParser = new ResponseParser();

    public PasswordRequestStep(MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    public PasswordRequestStepOutput execute(MulticodeRequestStepOutput output, Session session, char[] pesel, char[] password) {
        var request = request(output, session, pesel, password);
        var response = milleniumWebPageClient.performPasswordRequest(request);
        return new PasswordRequestStepOutput(response);
    }

    private PasswordRequest request(MulticodeRequestStepOutput output, Session session, char[] pesel, char[] password) {
        var documentHandler = new DocumentParser(responseParser, output.response);

        return new PasswordRequest(
                getPeselInputFormDataMap(documentHandler.findElementsBySelector("input[name~=PESEL*]"), pesel),
                documentHandler.findValueOfInputByName(RequestParams.VERIFICATION_TOKEN_PARAM),
                documentHandler.findValueOfInputByName(RequestParams.BOT_DETECTION_TOKEN_PARAM),
                documentHandler.findValueOfInputByName(RequestParams.LOGIN_CHALLENGE_PARAM),
                new HashMap<>(session.cookies),
                password);
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
