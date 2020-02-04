package com.dawhey.challenge.unit.service;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.client.RequestParams;
import com.dawhey.challenge.service.AccountsService;
import com.dawhey.challenge.step.output.Session;
import com.dawhey.challenge.util.ResponseParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountsServiceTest {

    public static final String ACCOUNT_1_NAME = "Konto 360°";

    public static final String ACCOUNT_2_NAME = "Konto 720°";

    public static final String ACCOUNT_1_BALANCE = "12312.22";

    public static final String ACCOUNT_2_BALANCE = "211342.33°";

    public static final String ACCOUNT_PAGE_STEP_HTML = String.format("<div class='RowEven'>" +
            "<div class='TemplateColumn col1 hidden-xs'><a class='MNHLink MNCommand TitleLink'>%s</a><br><span" +
            "            class='MNText FullAccountNumber NumberSmallDescription Text' data-dateformat='yy-mm-dd'></span></div>" +
            "<div class='TemplateColumn col2 AlignRight hidden-xs'><span class='MNText LargeAmount Amount'" +
            "            data-currencydecimalseparator=',' data-currencygroupseparator='&nbsp;' data-dateformat='yy-mm-dd'" +
            "            data-formattype='Amount' data-text='%s'><span class='val'></span><span class='cur'>" +
            "                PLN</span></span></div>" +
            "</div>" +
            "<div class='RowEven'>" +
            "<div class='TemplateColumn col1 hidden-xs'><a class='MNHLink MNCommand TitleLink'>%s</a><br><span" +
            "            class='MNText FullAccountNumber NumberSmallDescription Text' data-dateformat=' yy-mm-dd'></span></div>" +
            "<div class='TemplateColumn col2 AlignRight hidden-xs'><span class='MNText LargeAmount Amount'" +
            "            data-currencydecimalseparator=',' data-currencygroupseparator='&nbsp;' data-dateformat='yy-mm-dd'" +
            "            data-formattype='Amount' data-text='%s'><span class='val'></span><span class='cur'>" +
            "                PLN</span></span></div>" +
            "</div>", ACCOUNT_1_NAME, ACCOUNT_1_BALANCE, ACCOUNT_2_NAME, ACCOUNT_2_BALANCE);

    @Mock
    private ResponseParser responseParser;

    @Mock
    private MilleniumWebPageClient webPageClient;

    private AccountsService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new AccountsService(webPageClient, responseParser);
    }

    @Test
    public void shouldExtractAccountSet_whenProvidedWithCorrectHtml() throws MalformedURLException {
        //given
        var response = mock(Connection.Response.class);
        when(response.url()).thenReturn(new URL(RequestParams.MILLENIUM_BASE_URL + "osobiste/"));
        when(responseParser.parse(any())).thenReturn(Jsoup.parse(ACCOUNT_PAGE_STEP_HTML));

        //when
        var accounts = underTest.extractAccounts(new Session(null));

        //then
        assertEquals(2, accounts.size());
        assertTrue(accounts.stream().anyMatch(account -> account.name.equals(ACCOUNT_1_NAME) && account.balance.equals(ACCOUNT_1_BALANCE)));
        assertTrue(accounts.stream().anyMatch(account -> account.name.equals(ACCOUNT_2_NAME) && account.balance.equals(ACCOUNT_2_BALANCE)));
    }
}