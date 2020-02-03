package com.dawhey.challenge.service;

import com.dawhey.challenge.model.Credentials;
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
    private LoginService loginService;

    @Mock
    private AccountsService accountsService;

    @BeforeEach
    public void setUp() {
        underTest = new ScrapingService(accountsService, loginService);
    }

    @Test
    public void shouldRunStepsInOrder_whenScrapeMethodCalled() {
        //given
        var responseMock = mock(Connection.Response.class);
        var credentials = new Credentials(String.valueOf(MILLEKOD), String.valueOf(PASSWORD), String.valueOf(PESEL));
        //when
        var inOrder = inOrder(loginService, accountsService);
        underTest.scrapeBankPageForAccountDetails(credentials);

        //then
        inOrder.verify(loginService).login(credentials);
        inOrder.verify(accountsService).scrape(any());
    }

}