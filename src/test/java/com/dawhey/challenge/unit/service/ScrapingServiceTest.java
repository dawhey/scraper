package com.dawhey.challenge.unit.service;

import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.service.AccountsService;
import com.dawhey.challenge.service.LoginService;
import com.dawhey.challenge.service.ScrapingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.unit.TestUtil.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.inOrder;


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
        var credentials = new Credentials(String.valueOf(MILLEKOD), String.valueOf(PASSWORD), String.valueOf(PESEL));

        //when
        var inOrder = inOrder(loginService, accountsService);
        underTest.scrapeBankPageForAccountDetails(credentials);

        //then
        inOrder.verify(loginService).login(credentials);
        inOrder.verify(accountsService).extractAccounts(any());
    }

}