package com.dawhey.challenge.step;

import com.dawhey.challenge.TestUtil;
import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.util.ResponseParser;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.TestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MulticodeRequestStepTest {


    @InjectMocks
    private MulticodeRequestStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @Mock
    private ResponseParser responseParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPerformMultiCodeRequest_whenExecuted() {
        //given
        when(responseParser.parse(any())).thenReturn(Jsoup.parse(MULTICODE_STEP_HTML));

        //when
        underTest.execute(new WelcomePageStepResultOutput(null), new Session(TestUtil.welcomePageCookies()), MILLEKOD);

        //then
        verify(milleniumWebPageClient).performMultiCodeRequest(multicodeRequest());
    }

    private MulticodeRequest multicodeRequest() {
        return new MulticodeRequest(TestUtil.welcomePageCookies(), REQUEST_VERIFICATION_TOKEN, MILLEKOD);
    }
}