package com.dawhey.challenge.service;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.output.Session;
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

import java.net.MalformedURLException;
import java.net.URL;

import static com.dawhey.challenge.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountsServiceTest {

    @Mock
    private ResponseParser responseParser;

    @Mock
    private MilleniumWebPageClient webPageClient;

    @InjectMocks
    private AccountsService underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvertToAccountSet_whenProvidedWithCorrectHtml() throws MalformedURLException {
        //given
        var response = mock(Connection.Response.class);
        when(response.url()).thenReturn(new URL(RequestParams.MILLENIUM_BASE_URL + "osobiste/"));
        when(responseParser.parse(any())).thenReturn(Jsoup.parse(ACCOUNT_PAGE_STEP_HTML));

        //when
        var accounts = underTest.scrape(new Session(null));

        //then
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(new Account(ACCOUNT_1_NAME, ACCOUNT_1_BALANCE)));
        assertTrue(accounts.contains(new Account(ACCOUNT_2_NAME, ACCOUNT_2_BALANCE)));
    }
}