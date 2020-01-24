package com.dawhey.challenge.step;

import com.dawhey.challenge.client.MilleniumWebPageClient;
import com.dawhey.challenge.model.Account;
import com.dawhey.challenge.step.result.PasswordRequestStepResultSession;
import com.dawhey.challenge.step.result.Session;
import com.dawhey.challenge.util.DocumentParser;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static com.dawhey.challenge.util.TestUtil.*;

@ExtendWith(SpringExtension.class)
class AccountPageStepTest {

    @Mock
    private DocumentParser documentParser;

    @Mock
    private MilleniumWebPageClient webPageClient;

    @InjectMocks
    private AccountPageStep underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvertToAccountSet_whenProvidedWithCorrectHtml() {
        //given
        when(documentParser.parseFrom(any())).thenReturn(Jsoup.parse(ACCOUNT_PAGE_STEP_HTML));

        //when
        var accounts = underTest.execute(session());

        //then
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(new Account(ACCOUNT_1_NAME, ACCOUNT_1_BALANCE)));
        assertTrue(accounts.contains(new Account(ACCOUNT_2_NAME, ACCOUNT_2_BALANCE)));
    }

    private PasswordRequestStepResultSession session() {
        return new PasswordRequestStepResultSession(new Session(new HashMap<>(), any()));
    }




}