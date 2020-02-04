package com.dawhey.challenge.unit.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.unit.TestUtil;
import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WelcomePageStepTest {

    private WelcomePageStep underTest;

    @Mock
    private MilleniumWebPageClient milleniumWebPageClient;

    @BeforeEach
    void setUp() {
        underTest = new WelcomePageStep(milleniumWebPageClient);
    }

    @Test
    public void shouldGetWelcomePage_whenExecuted() {
        //given
        var responseMock = mock(Connection.Response.class);
        when(responseMock.cookies()).thenReturn(TestUtil.welcomePageCookies());
        when(milleniumWebPageClient.getMilleniumWelcomePage()).thenReturn(responseMock);

        //when
        var result = underTest.execute();

        //then
        verify(milleniumWebPageClient).getMilleniumWelcomePage();
        assertEquals(TestUtil.welcomePageCookies(), result.response.cookies());
    }


}