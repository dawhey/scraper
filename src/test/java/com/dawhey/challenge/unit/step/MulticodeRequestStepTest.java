package com.dawhey.challenge.unit.step;

import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.unit.TestUtil;
import com.dawhey.challenge.web.client.MilleniumWebPageClient;
import com.dawhey.challenge.web.parser.ScraperDocument;
import com.dawhey.challenge.web.request.MulticodeRequestPayload;
import com.dawhey.challenge.web.response.Response;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static com.dawhey.challenge.unit.TestUtil.MILLEKOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MulticodeRequestStepTest {

    public static final String REQUEST_VERIFICATION_TOKEN = "E2I7aF4vwiJCfdWEq3s7XG3jbo1xmxaFHwvojwljHyBQ8XWBblsWmg0OhZjfs0wqo6Ahk1g_-aIdA7Ap5jx4NiNGl9k1";

    public static final String MULTICODE_STEP_HTML = String.format("<input name='__RequestVerificationToken' " +
            "type='hidden' value='%s'>", REQUEST_VERIFICATION_TOKEN);

    private MulticodeRequestStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @BeforeEach
    public void setUp() {
        underTest = new MulticodeRequestStep(milleniumWebPageClient);
    }

    @Test
    public void shouldPerformMultiCodeRequest_whenExecuted() {
        //given
        var payloadCaptor = ArgumentCaptor.forClass(MulticodeRequestPayload.class);
        var cookiesCaptor = ArgumentCaptor.forClass(Map.class);
        var previousOutput = new WelcomePageStepResultOutput(new Response(null, new ScraperDocument(Jsoup.parse(MULTICODE_STEP_HTML))));

        //when
        underTest.execute(previousOutput, new Session(TestUtil.welcomePageCookies()), MILLEKOD);

        //then
        verify(milleniumWebPageClient).performMultiCodeRequest(payloadCaptor.capture(), cookiesCaptor.capture());
        var payload = payloadCaptor.getValue();
        var cookies = cookiesCaptor.getValue();

        assertEquals(TestUtil.welcomePageCookies(), cookies);
        assertEquals(MILLEKOD, payload.millekod);
        assertEquals(REQUEST_VERIFICATION_TOKEN, payload.verificationTokenValue);
    }
}