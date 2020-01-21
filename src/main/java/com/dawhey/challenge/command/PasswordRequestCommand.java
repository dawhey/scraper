package com.dawhey.challenge.command;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.util.DocumentHandler;
import com.dawhey.challenge.request.PasswordRequestData;
import com.dawhey.challenge.wrapper.PasswordRequestCommandDataWrapper;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PasswordRequestCommand extends ScrapingCommand<PasswordRequestCommandDataWrapper, Connection.Response> {

    /**
     * Index of first character of number in PESEL input name
     */
    public static final int PESEL_INPUT_NUMBER_INDEX = 6;

    @Value("${millenium.request.verification.token.name}")
    private String requestVerificationTokenName;

    @Value("${millenium.bot.detection.client.token.name}")
    private String botDetectionClientTokenName;

    @Value("${millenium.security.digits.view.model.login.challenge.parameter.name}")
    private String securityDigitsLoginChallengeParamName;

    private final MilleniumWebPageClient milleniumWebPageClient;

    public PasswordRequestCommand(final MilleniumWebPageClient milleniumWebPageClient) {
        this.milleniumWebPageClient = milleniumWebPageClient;
    }

    @Override
    protected Connection.Response executeLogic(final PasswordRequestCommandDataWrapper passwordRequestCommandData) throws Exception {
        final PasswordRequestData requestData = buildRequestData(passwordRequestCommandData);
        return milleniumWebPageClient.performPasswordRequest(requestData);
    }

    @Override
    protected String name() {
        return "Password Request Command";
    }

    private PasswordRequestData buildRequestData(final PasswordRequestCommandDataWrapper commandData) throws IOException {
        final DocumentHandler documentHandler = new DocumentHandler(commandData.getPreviousResponse().parse());

        return PasswordRequestData.builder()
                .peselFormData(getPeselInputFormDataMap(documentHandler.findElementsBySelector("input[name~=PESEL*]"), commandData.getPesel()))
                .botDetectionClientToken(documentHandler.findValueOfInputByName(botDetectionClientTokenName))
                .requestVerificationToken(documentHandler.findValueOfInputByName(requestVerificationTokenName))
                .securityDigitsLoginChallengeToken(documentHandler.findValueOfInputByName(securityDigitsLoginChallengeParamName))
                .cookies(commandData.getCookies())
                .password(commandData.getPassword())
                .build();
    }

    /**
     * @param peselInputList all pesel number inputs (disabled and enabled)
     * @return Map<PESEL_DIGIT_INDEX, PESEL_VALUE_AT_INDEX> - map of form data that should be filled in by user eg. (("PESEL_6", "2"), ("PESEL_10", "9"));
     */
    private Map<String, String> getPeselInputFormDataMap(final List<Element> peselInputList, final char[] pesel) {
        return peselInputList.stream()
                .filter(element -> !element.attributes().hasKey("disabled"))
                .collect(Collectors.toMap(
                        element -> element.attr("name"),
                        element -> String.valueOf(pesel[Integer.parseInt(element.attr("name").substring(PESEL_INPUT_NUMBER_INDEX))])));
    }
}
