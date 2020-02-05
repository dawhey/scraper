package com.dawhey.challenge.unit;

import com.dawhey.challenge.Scraper;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.provider.CredentialsProvider;
import com.dawhey.challenge.service.ScrapingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.unit.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ScraperTest {

    private Scraper underTest;

    @Mock
    private ScrapingService scrapingService;

    @Mock
    private CredentialsProvider credentialsProvider;

    @BeforeEach
    public void setUp() {
        underTest = new Scraper(scrapingService, credentialsProvider);
    }

    @Test
    public void shouldScrapeUsingCredentialsFromProvider_whenRun() {
        //given
        var credentialsCaptor = ArgumentCaptor.forClass(Credentials.class);
        when(credentialsProvider.credentials()).thenReturn(new Credentials(String.valueOf(MILLEKOD), String.valueOf(PASSWORD), String.valueOf(PESEL)));

        //when
        underTest.run();

        //then
        verify(scrapingService).scrapeBankPageForAccountDetails(credentialsCaptor.capture());
        var credentials = credentialsCaptor.getValue();
        assertArrayEquals(MILLEKOD, credentials.millekod);
        assertArrayEquals(PASSWORD, credentials.password);
        assertArrayEquals(PESEL, credentials.pesel);

    }
}