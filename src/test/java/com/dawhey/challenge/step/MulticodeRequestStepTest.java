package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.MulticodeRequest;
import com.dawhey.challenge.step.result.WelcomePageStepResultSession;
import com.dawhey.challenge.util.DocumentParser;
import com.dawhey.challenge.util.TestUtil;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.dawhey.challenge.util.TestUtil.*;
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
    private DocumentParser documentParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPerformCorrectRequest_whenExecuted() {
        //given
        when(documentParser.parseFrom(any())).thenReturn(Jsoup.parse(MULTICODE_STEP_HTML));

        //when
        underTest.execute(new WelcomePageStepResultSession(TestUtil.welcomePageCookies(), any()), MILLEKOD);

        //then
        verify(milleniumWebPageClient).performMultiCodeRequest(multicodeRequest());
    }

    private MulticodeRequest multicodeRequest() {
        return MulticodeRequest.builder()
                .cookies(TestUtil.welcomePageCookies())
                .millekod(MILLEKOD)
                .verificationTokenValue(REQUEST_VERIFICATION_TOKEN)
                .build();
    }
}