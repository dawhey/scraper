package com.dawhey.challenge.service;

import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.step.AccountPageStep;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.util.TestUtil.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class ScrapingServiceTest {

    @InjectMocks
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldRunStepsInOrder_whenScrapeMethodCalled() {
        var inOrder = inOrder(welcomePageStep, multicodeRequestStep, passwordRequestStep, accountPageStep);
        underTest.scrapeBankPageForAccountDetails(new Credentials(MILLEKOD, PASSWORD, PESEL));

        inOrder.verify(welcomePageStep).execute();
        inOrder.verify(multicodeRequestStep).execute(any(), eq(MILLEKOD));
        inOrder.verify(passwordRequestStep).execute(any(), eq(PESEL), eq(PASSWORD));
        inOrder.verify(accountPageStep).execute(any());
    }

}