package com.dawhey.challenge.service;

import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.step.AccountPageStep;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import org.jsoup.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.TestUtil.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class ScrapingServiceTest {

    private ScrapingService underTest;

    @Mock
    private WelcomePageStep welcomePageStep;

    @Mock
    private MulticodeRequestStep multicodeRequestStep;

    @Mock
    private PasswordRequestStep passwordRequestStep;

    @Mock
    private AccountPageStep accountPageStep;

    @BeforeEach
    public void setUp() {
        underTest = new ScrapingService(welcomePageStep, multicodeRequestStep, passwordRequestStep, accountPageStep);
    }

    @Test
    public void shouldRunStepsInOrder_whenScrapeMethodCalled() {
        //given
        var responseMock = mock(Connection.Response.class);
        when(welcomePageStep.execute()).thenReturn(new WelcomePageStepResultOutput(responseMock));
        when(multicodeRequestStep.execute(any(), any(), any())).thenReturn(new MulticodeRequestStepOutput(responseMock));
        when(passwordRequestStep.execute(any(), any(), any(), any())).thenReturn(new PasswordRequestStepOutput(responseMock));

        //when
        var inOrder = inOrder(welcomePageStep, multicodeRequestStep, passwordRequestStep, accountPageStep);
        underTest.scrapeBankPageForAccountDetails(new Credentials(String.valueOf(MILLEKOD), String.valueOf(PASSWORD), String.valueOf(PESEL)));

        //then
        inOrder.verify(welcomePageStep).execute();
        inOrder.verify(multicodeRequestStep).execute(any(), any(), eq(MILLEKOD));
        inOrder.verify(passwordRequestStep).execute(any(), any(), eq(PESEL), eq(PASSWORD));
        inOrder.verify(accountPageStep).execute(any(), any());
    }

}