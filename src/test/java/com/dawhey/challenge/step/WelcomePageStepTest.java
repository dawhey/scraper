package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.step.result.ScrapingSession;
import com.dawhey.challenge.util.TestUtil;
import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WelcomePageStepTest {

    @InjectMocks
    private WelcomePageStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @Mock
    private ScrapingSession scrapingSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPerformClientRequest_whenCalled() {
        //given
        var responseMock = mock(Connection.Response.class);
        when(responseMock.cookies()).thenReturn(TestUtil.welcomePageCookies());
        when(milleniumWebPageClient.getMilleniumWelcomePage()).thenReturn(responseMock);

        //when
        var result = underTest.execute();

        //then
        verify(milleniumWebPageClient).getMilleniumWelcomePage();
        assertEquals(TestUtil.welcomePageCookies(), result.getCookies());
    }


}