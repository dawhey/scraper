package com.dawhey.challenge.unit.service;

import com.dawhey.challenge.exception.LoginFailureException;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.model.Response;
import com.dawhey.challenge.service.LoginService;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dawhey.challenge.unit.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    public static final String HTML_WITHOUT_LOGOUT_BUTTON = "<div class='y6gkzs'>" +
            "    <div class='c7c0bi0'>" +
            "        <div class='ggk105'>" +
            "            <div class='c-6pr051'>" +
            "                <div data-element-id='menu-link-messages'><a" +
            "                        href='https://www.bankmillennium.pl/osobiste2/Messaging/Inbox/MessagesList'" +
            "                        class='c-bbjqe4 c-qqp15e' target='_self' data-element-id='menu-link-messages'>" +
            "                        <div class='c10uanh'>" +
            "                            <div class='xexqg' data-element-id='menu-info-unread-messages'>29</div>" +
            "                        </div>" +
            "                    </a>" +
            "                </div>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

    public static final String HTML_WITH_LOGOUT_BUTTON = "<div class='y6gkzs'>" +
            "    <div class='c7c0bi0'>" +
            "        <div class='ggk105'>" +
            "            <div class='c-6pr051'>" +
            "                <div data-element-id='menu-link-messages'><a" +
            "                        href='https://www.bankmillennium.pl/osobiste2/Messaging/Inbox/MessagesList'" +
            "                        class='c-bbjqe4 c-qqp15e' target='_self' data-element-id='menu-link-messages'>" +
            "                        <div class='c10uanh'>" +
            "                            <div class='xexqg' data-element-id='menu-info-unread-messages'>29</div>" +
            "                        </div>" +
            "                    </a>" +
            "                </div>" +
            "            </div>" +
            "            <div class='vmk7f1'>" +
            "                <div class='lwflt'><a href='https://www.bankmillennium.pl/osobiste2/Retail/Logout' aria-label='Wyloguj'" +
            "                        target='_self' class='c-bbjqe4' data-element-id='menu-link-logout'>" +
            "                        <div class='df6q61' data-element-id='menu-link-logout'><svg class='til5u0'" +
            "                                viewBox='0 0 20.76027 22.58333'>" +
            "                            " +
            "                            </svg></div>" +
            "                        <div class='eonskz'>" +
            "                            <div class='dfdsq5' data-element-id='menu-info-logout-text'>Wyloguj</div>" +
            "                            <div class='c6bagl8' data-element-id='menu-info-time-left'>04:07</div>" +
            "                        </div>" +
            "                    </a></div>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

    private LoginService underTest;

    @Mock
    private MulticodeRequestStep multicodeRequestStep;

    @Mock
    private WelcomePageStep welcomePageStep;

    @Mock
    private PasswordRequestStep passwordRequestStep;

    private Response passwordResponseStub;

    @BeforeEach
    public void setUp() {
        underTest = new LoginService(welcomePageStep, multicodeRequestStep, passwordRequestStep);

        var welcomePageResponseStub = new Response(welcomePageCookies(), null);
        var multicodeResponseStub = new Response(multicodeCookies(), null);
        passwordResponseStub = new Response(signInCookies(), new ScraperDocument(Jsoup.parse(HTML_WITH_LOGOUT_BUTTON)));

        when(welcomePageStep.execute()).thenReturn(new WelcomePageStepResultOutput(welcomePageResponseStub));
        when(multicodeRequestStep.execute(any(), any(), any())).thenReturn(new MulticodeRequestStepOutput(multicodeResponseStub));
        when(passwordRequestStep.execute(any(), any(), any(), any())).thenReturn(new PasswordRequestStepOutput(passwordResponseStub));

    }

    @Test
    public void shouldPassLoginValidation_whenLogoutButtonExist() {
        assertDoesNotThrow(() -> underTest.login(new Credentials("some-millekod", "some-password", "some-pesel")));
    }

    @Test
    public void shouldFailLoginValidation_whenNoLogoutButtonExists() {
        passwordResponseStub = new Response(signInCookies(), new ScraperDocument(Jsoup.parse(HTML_WITHOUT_LOGOUT_BUTTON)));
        when(passwordRequestStep.execute(any(), any(), any(), any())).thenReturn(new PasswordRequestStepOutput(passwordResponseStub));

        assertThrows(LoginFailureException.class, () -> underTest.login(new Credentials("some-millekod", "some-password", "some-pesel")));
    }

    @Test
    public void shouldReturnJoinedCookiesSession_whenCalled() {
        var session = underTest.login(new Credentials("some-millekod", "some-password", "some-pesel"));

        assertTrue(session.cookies.values().containsAll(welcomePageCookies().values()));
        assertTrue(session.cookies.values().containsAll(multicodeCookies().values()));
        assertTrue(session.cookies.values().containsAll(signInCookies().values()));
    }
}