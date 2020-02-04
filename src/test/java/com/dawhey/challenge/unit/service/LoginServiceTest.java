package com.dawhey.challenge.unit.service;

import com.dawhey.challenge.exception.LoginFailureException;
import com.dawhey.challenge.model.Credentials;
import com.dawhey.challenge.service.LoginService;
import com.dawhey.challenge.step.MulticodeRequestStep;
import com.dawhey.challenge.step.PasswordRequestStep;
import com.dawhey.challenge.step.WelcomePageStep;
import com.dawhey.challenge.step.output.MulticodeRequestStepOutput;
import com.dawhey.challenge.step.output.PasswordRequestStepOutput;
import com.dawhey.challenge.step.output.WelcomePageStepResultOutput;
import com.dawhey.challenge.util.ResponseParser;
import com.dawhey.challenge.util.ScraperDocument;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private ResponseParser responseParser;

    @Mock
    private Connection.Response passwordResponseMock;

    @Mock
    private Connection.Response someResponse;

    @BeforeEach
    public void setUp() {
        underTest = new LoginService(welcomePageStep, multicodeRequestStep, passwordRequestStep, responseParser);
        when(welcomePageStep.execute()).thenReturn(new WelcomePageStepResultOutput(someResponse));
        when(multicodeRequestStep.execute(any(), any(), any())).thenReturn(new MulticodeRequestStepOutput(someResponse));
        when(passwordRequestStep.execute(any(), any(), any(), any())).thenReturn(new PasswordRequestStepOutput(passwordResponseMock));
    }

    @Test
    public void shouldPassLoginValidation_whenLogoutButtonExist() {
        when(responseParser.parse(passwordResponseMock)).thenReturn(new ScraperDocument(Jsoup.parse(HTML_WITH_LOGOUT_BUTTON)));
        assertDoesNotThrow(() -> underTest.login(new Credentials("some-millekod", "some-password", "some-pesel")));
    }

    @Test
    public void shouldFailLoginValidation_whenNoLogoutButtonExists() {
        when(responseParser.parse(passwordResponseMock)).thenReturn(new ScraperDocument(Jsoup.parse(HTML_WITHOUT_LOGOUT_BUTTON)));
        assertThrows(LoginFailureException.class, () -> underTest.login(new Credentials("some-millekod", "some-password", "some-pesel")));
    }
}