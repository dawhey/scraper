package com.dawhey.challenge.unit.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.unit.TestUtil;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.unit.TestUtil.MILLEKOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MulticodeRequestStepTest {

    public static final String REQUEST_VERIFICATION_TOKEN = "E2I7aF4vwiJCfdWEq3s7XG3jbo1xmxaFHwvojwljHyBQ8XWBblsWmg0OhZjfs0wqo6Ahk1g_-aIdA7Ap5jx4NiNGl9k1";

    public static final String MULTICODE_STEP_HTML = String.format("<input name='__RequestVerificationToken' " +
            "type='hidden' value='%s'>", REQUEST_VERIFICATION_TOKEN);

    private MulticodeRequestStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @Mock
    private ResponseParser responseParser;

    @BeforeEach
    public void setUp() {
        underTest = new MulticodeRequestStep(milleniumWebPageClient, responseParser);
    }

    @Test
    public void shouldPerformMultiCodeRequest_whenExecuted() {
        //given
        var requestCaptor = ArgumentCaptor.forClass(MulticodeRequest.class);
        when(responseParser.parse(any())).thenReturn(new ScraperDocument(Jsoup.parse(MULTICODE_STEP_HTML)));

        //when
        underTest.execute(new WelcomePageStepResultOutput(null), new Session(TestUtil.welcomePageCookies()), MILLEKOD);

        //then
        verify(milleniumWebPageClient).performMultiCodeRequest(requestCaptor.capture());
        var request = requestCaptor.getValue();

        assertEquals(TestUtil.welcomePageCookies(), request.cookies);
        assertEquals(MILLEKOD, request.millekod);
        assertEquals(REQUEST_VERIFICATION_TOKEN, request.verificationTokenValue);
    }
}