package com.dawhey.challenge.unit.step;

import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.unit.TestUtil;
import com.dawhey.challenge.web.client.MilleniumWebPageClient;
import com.dawhey.challenge.web.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        var responseStub = new Response(TestUtil.welcomePageCookies(), null);
        when(milleniumWebPageClient.getMilleniumWelcomePage()).thenReturn(responseStub);

        //when
        var result = underTest.execute();

        //then
        verify(milleniumWebPageClient).getMilleniumWelcomePage();
        assertEquals(TestUtil.welcomePageCookies(), result.response.cookies);
    }


}