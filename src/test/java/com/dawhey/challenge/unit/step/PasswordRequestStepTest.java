package com.dawhey.challenge.unit.step;

import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.web.client.MilleniumWebPageClient;
import com.dawhey.challenge.web.parser.ScraperDocument;
import com.dawhey.challenge.web.request.PasswordRequestPayload;
import com.dawhey.challenge.web.response.Response;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static com.dawhey.challenge.unit.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
class PasswordRequestStepTest {

    public static final String BOT_DETECTION_CLIENT_TOKEN = "7b1b3179-4afc-4d84-b187-73a596b45086";

    public static final String SECURITY_DIGITS_LOGIN_CHALLENGE = "7;1;";

    public static final String REQUEST_VERIFICATION_TOKEN = "E2I7aF4vwiJCfdWEq3s7XG3jbo1xmxaFHwvojwljHyBQ8XWBblsWmg0OhZjfs0wqo6Ahk1g_-aIdA7Ap5jx4NiNGl9k1";

    public static final String PASSWORD_STEP_HTML = String.format("<div class='SecurityDigits'>" +
            "        <input id='BotDetection_ClientToken' name='BotDetection.ClientToken' type='hidden'" +
            "                value='%s'>" +
            "<input id='__RequestVerificationToken' name='__RequestVerificationToken' type='hidden'" +
            "                value='%s'>" +
            "        <input id='SecurityDigitsViewModel_LoginChallenge' name='SecurityDigitsViewModel.LoginChallenge' type='hidden'" +
            "                value='%s'>" +
            "        <div class='MNMaskedPasswordItem Active ItemContainer' data-allowedcharacters='DigitsOnly' data-id='PESEL'>" +
            "                <div class='MNTextBox MNField Position Disabled Striped LeftEdge form-group DescriptionTop'" +
            "                        data-allowedcharacters='DigitsOnly' data-maskautoclear='true' data-readonly='True'" +
            "                        data-watermarkalign='notset' id='PESEL_0'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_0_txtContent'>1</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_0_txtContent' maxlength='1' name='PESEL_0'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div class='MNTextBox MNField Position Enabled form-group DescriptionTop'" +
            "                        data-allowedcharacters='DigitsOnly' data-appearance='focusbox' data-maskautoclear='true'" +
            "                        data-order='1' data-readonly='False' data-watermarkalign='notset' data-widget='true'" +
            "                        id='PESEL_1'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_1_txtContent'>2<span class='aria-hide'>#(Accessibility" +
            "                                                description)</span></label></div>" +
            "                        <div><input id='PESEL_1_txtContent' maxlength='1' name='PESEL_1' style='text-align: center;'" +
            "                                type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_2'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_2_txtContent'>3</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_2_txtContent' maxlength='1' name='PESEL_2'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_3'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_3_txtContent'>4</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_3_txtContent' maxlength='1' name='PESEL_3'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_4'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_4_txtContent'>5</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_4_txtContent' maxlength='1' name='PESEL_4'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_5'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_5_txtContent'>6</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_5_txtContent' maxlength='1' name='PESEL_5'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_6'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_6_txtContent'>7</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_6_txtContent' maxlength='1' name='PESEL_6'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_7'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_7_txtContent'>8</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_7_txtContent' maxlength='1' name='PESEL_7'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_8'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_8_txtContent'>9</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_8_txtContent' maxlength='1' name='PESEL_8'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_9'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_9_txtContent'>10</label></div>" +
            "                        <div><input disabled='disabled' id='PESEL_9_txtContent' maxlength='1' name='PESEL_9'" +
            "                                style='text-align: center;' type='password'></div>" +
            "                </div>" +
            "                <div id='PESEL_10'>" +
            "                        <div class='col-xs-12 control-label Top Description'><label class='Label'" +
            "                                        for='PESEL_10_txtContent'>11</label></div>" +
            "                        <div><input id='PESEL_10_txtContent' maxlength='1' name='PESEL_10' style='text-align: center;'" +
            "                                type='password'></div>" +
            "                </div>" +
            "        </div>" +
            "</div>", BOT_DETECTION_CLIENT_TOKEN, REQUEST_VERIFICATION_TOKEN, SECURITY_DIGITS_LOGIN_CHALLENGE);

    private PasswordRequestStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @BeforeEach
    public void setUp() {
        underTest = new PasswordRequestStep(milleniumWebPageClient);
    }

    @Test
    public void shouldBuildCorrectPasswordRequest_whenExecuted() {
        //given
        var payloadCaptor = ArgumentCaptor.forClass(PasswordRequestPayload.class);
        var cookiesCaptor = ArgumentCaptor.forClass(Map.class);
        var previousResponseStub = new Response(multicodeCookies(), new ScraperDocument(Jsoup.parse(PASSWORD_STEP_HTML)));

        //when
        underTest.execute(new MulticodeRequestStepOutput(previousResponseStub), new Session(welcomePageCookies()), PESEL, PASSWORD);

        //then
        verify(milleniumWebPageClient).performPasswordRequest(payloadCaptor.capture(), cookiesCaptor.capture());
        var payload = payloadCaptor.getValue();
        var cookies = cookiesCaptor.getValue();

        assertEquals(REQUEST_VERIFICATION_TOKEN, payload.requestVerificationToken);
        assertEquals(BOT_DETECTION_CLIENT_TOKEN, payload.botDetectionClientToken);
        assertEquals(SECURITY_DIGITS_LOGIN_CHALLENGE, payload.securityDigitsLoginChallengeToken);
        assertEquals(PASSWORD, payload.password);
        assertEquals(peselFormData(), payload.peselFormData);
        assertEquals(welcomePageCookies(), cookies);
    }

    private static Map<String, String> peselFormData() {
        return new HashMap<>() {{
            put("PESEL_1", "2");
            put("PESEL_10", "1");
        }};
    }
}