package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.request.PasswordRequest;
import com.dawhey.challenge.step.result.MulticodeRequestStepResultSession;
import com.dawhey.challenge.step.result.Session;
import com.dawhey.challenge.util.ResponseParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static com.dawhey.challenge.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class PasswordRequestStepTest {


    @InjectMocks
    private PasswordRequestStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @Mock
    private ResponseParser responseParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPerformCorrectRequest_whenExecuted() {
        //given
        var responseMock = mock(Connection.Response.class);
        when(responseMock.cookies()).thenReturn(signInCookies());
        when(milleniumWebPageClient.performPasswordRequest(any())).thenReturn(responseMock);
        when(responseParser.parse(any())).thenReturn(Jsoup.parse(PASSWORD_STEP_HTML));

        //when
        var result = underTest.execute(new MulticodeRequestStepResultSession(new Session(welcomePageCookies(), any())), PESEL, PASSWORD);

        //then
        verify(milleniumWebPageClient).performPasswordRequest(passwordRequest());
        assertTrue(result.getCookies().entrySet().containsAll(welcomePageCookies().entrySet()));
        assertTrue(result.getCookies().entrySet().containsAll(signInCookies().entrySet()));
    }

    private PasswordRequest passwordRequest() {
        var peselFormData = new HashMap<String, String>() {{
           put("PESEL_1", "2");
           put("PESEL_10", "1");
        }};

        return PasswordRequest.builder()
                .botDetectionClientToken(BOT_DETECTION_CLIENT_TOKEN)
                .cookies(welcomePageCookies())
                .requestVerificationToken(REQUEST_VERIFICATION_TOKEN)
                .securityDigitsLoginChallengeToken(SECURITY_DIGITS_LOGIN_CHALLENGE)
                .password(PASSWORD)
                .peselFormData(peselFormData)
                .build();
    }
}